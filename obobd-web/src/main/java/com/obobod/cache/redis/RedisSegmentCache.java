package com.obobod.cache.redis;


import mondrian.spi.SegmentBody;
import mondrian.spi.SegmentCache;
import mondrian.spi.SegmentHeader;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class RedisSegmentCache implements SegmentCache {

    private static final Logger logger = Logger.getLogger(RedisSegmentCache.class);
    private final List<SegmentCacheListener> listeners =
            new CopyOnWriteArrayList<SegmentCacheListener>();

    private static final RedisDao getCache() {
        return new RedisDao();
    }

    public SegmentBody get(SegmentHeader header) {

        if (header == null)
            return null;

        final byte[] ref = getCache().get(getHeaderKey(header));

        if(ref ==null){
            return null;
        }

        final SegmentBody body = (SegmentBody) SerializeUtil.unserialize(ref);

        if (body == null) {
            return null;
        } else {
            Map valuemap = body.getValueMap();
            if (valuemap == null || valuemap.size() == 0) {
                this.remove(header);
                logger.info("RedisSegmentCache execute valuemap==null||valuemap.size()==0,this.remove(header)!");
            } else {
                logger.info("RedisSegmentCache execute get sucess!");
            }

        }

        return body;
    }

    public boolean contains(SegmentHeader header) {
        if (header == null)
            return false;

        final byte[] ref = getCache().get(getHeaderKey(header));
        if (ref == null) {
            return false;
        }
        final SegmentBody body = (SegmentBody) SerializeUtil.unserialize(ref);
        if (body == null) {
            try {
                this.remove(header);
            } catch (Exception e) {
                logger.error("SegmentBody contains error：" + e.getMessage());
            }
            return false;
        }
        logger.info("RedisSegmentCache execute contains sucess!");
        return true;
    }

    @Override
    public List<SegmentHeader> getSegmentHeaders() {
        try {

            List<SegmentHeader> retList = new ArrayList<SegmentHeader>(  );

            for(Iterator<String> i= getCache().keySet().iterator();i.hasNext();){
                retList.add((SegmentHeader)SerializeUtil.unserialize(i.next().getBytes()));
            }

            logger.info("获得Segement headers" + retList.size());
            return retList;


        } catch ( Exception e ) {
            logger.error(e);
            return Collections.emptyList();
        }


    }

    public boolean put(final SegmentHeader header, SegmentBody body) {
        assert header != null;
        assert body != null;

        try {
            getCache().put(getHeaderKey(header), SerializeUtil.serialize(body));
        } catch (Exception e) {
            logger.error("SegmentBody put error：" + e.getMessage());
        }
        fireSegmentCacheEvent(
                new SegmentCache.SegmentCacheListener.SegmentCacheEvent() {
                    public boolean isLocal() {
                        return false;
                    }

                    public SegmentHeader getSource() {
                        return header;
                    }

                    public EventType getEventType() {
                        return SegmentCacheListener.SegmentCacheEvent
                                .EventType.ENTRY_CREATED;
                    }
                });
        logger.info("RedisSegmentCache execute put sucess!");
        return true; // success
    }

    public boolean remove(final SegmentHeader header) {
        if (header == null)
            return false;

        boolean result = false;
        try {
            result = getCache().remove(getHeaderKey(header)) != null;

//	        if (result) {
            fireSegmentCacheEvent(
                    new SegmentCache.SegmentCacheListener.SegmentCacheEvent() {
                        public boolean isLocal() {
                            return true;
                        }

                        public SegmentHeader getSource() {
                            return header;
                        }

                        public EventType getEventType() {
                            return
                                    SegmentCacheListener.SegmentCacheEvent
                                            .EventType.ENTRY_DELETED;
                        }
                    });
//	        }
        } catch (Exception e) {
            logger.error("SegmentBody remove error：" + e.getMessage());
        }
        logger.info("RedisSegmentCache execute remove sucess!");
        return result;
    }

    private byte[] getHeaderKey(SegmentHeader header) {
        header.getDescription();
        return SerializeUtil.serialize(header);
    }

    public void tearDown() {

        getCache().clear();
        listeners.clear();
        logger.info("RedisSegmentCache execute tearDown sucess!");
    }

    public void addListener(SegmentCacheListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SegmentCacheListener listener) {
        listeners.remove(listener);
    }

    public boolean supportsRichIndex() {
        return true;
    }

    public void fireSegmentCacheEvent(
            SegmentCache.SegmentCacheListener.SegmentCacheEvent evt) {
        for (SegmentCacheListener listener : listeners) {
            listener.handle(evt);
        }
    }
}
