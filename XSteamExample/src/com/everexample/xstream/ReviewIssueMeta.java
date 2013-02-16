
package com.everexample.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * <br/>
 * ParseReview-com.baidu.navonline.parsereview.ReviewIssueMeta.java
 * 
 * @author <a href="mailto:zhanghanguo@baidu.com">zhanghanguo@baidu.com</a>
 * @version 2013-1-22下午1:30:44
 */
@XStreamAlias("ReviewIssueMeta")
public class ReviewIssueMeta {

    @XStreamConverter(value = ToAttributedValueConverter.class, strings = {
        "content"
    })
    public class DataTag {

        @XStreamAsAttribute
        @XStreamAlias("format")
        private String format;

        private String content;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    private DataTag CreationDate;

    private DataTag LastModificationDate;

    public ReviewIssueMeta() {
    }

    public DataTag getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(DataTag creationDate) {
        CreationDate = creationDate;
    }

    public DataTag getLastModificationDate() {
        return LastModificationDate;
    }

    public void setLastModificationDate(DataTag lastModificationDate) {
        LastModificationDate = lastModificationDate;
    }

}
