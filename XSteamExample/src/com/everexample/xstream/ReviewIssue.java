
package com.everexample.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * <br/>
 * ParseReview-com.baidu.navonline.parsereview.ReviewIssue.java
 * 
 * @author <a href="mailto:zhanghanguo@baidu.com">zhanghanguo@baidu.com</a>
 * @version 2013-1-22下午1:30:24
 */
@XStreamAlias("ReviewIssue")
public class ReviewIssue {

    /*
     * <ReviewIssue id="HC7ILJBE"> <ReviewIssueMeta> <CreationDate
     * format="yyyy-MM-dd :: HH:mm:ss:SSS z">2013-01-21 :: 19:11:18:794
     * CST</CreationDate> <LastModificationDate
     * format="yyyy-MM-dd :: HH:mm:ss:SSS z">2013-01-22 :: 11:42:04:885
     * CST</LastModificationDate> </ReviewIssueMeta>
     * <ReviewerId>zhanghanguo</ReviewerId> <AssignedTo>zhanghanguo</AssignedTo>
     * <File line="236">src/com/baidu/navi/ui/NaviActivity.java</File>
     * <Type>item.label.unset</Type> <Severity>item.label.unset</Severity>
     * <Summary>避免魔鬼数字</Summary> <Description>导航内的魔鬼数字定义</Description>
     * <Annotation></Annotation> <Revision></Revision>
     * <Resolution>item.label.unset</Resolution>
     * <Status>item.status.label.resolved</Status>
     */

    @XStreamAlias("File")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings = {
        "content"
    })
    public class FileTag {

        @XStreamAsAttribute
        @XStreamAlias("line")
        private String line;

        private String content;

        public FileTag(String line, String content) {
            super();
            this.line = line;
            this.content = content;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    @XStreamAsAttribute
    @XStreamAlias("id")
    private String id;

    private ReviewIssueMeta ReviewIssueMeta;

    private String ReviewerId;

    private String AssignedTo;

    @XStreamAlias("File")
    private FileTag fileTag;

    private String Type;

    private String Severity;

    private String Summary;

    private String Description;

    private String Annotation;

    private String Revision;

    private String Resolution;

    private String Status;

    public ReviewIssueMeta getReviewIssueMeta() {
        return ReviewIssueMeta;
    }

    public void setReviewIssueMeta(ReviewIssueMeta reviewIssueMeta) {
        ReviewIssueMeta = reviewIssueMeta;
    }

    public String getReviewerId() {
        return ReviewerId;
    }

    public void setReviewerId(String reviewerId) {
        ReviewerId = reviewerId;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public FileTag getFileTag() {
        return fileTag;
    }

    public void setFileTag(FileTag fileTag) {
        this.fileTag = fileTag;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAnnotation() {
        return Annotation;
    }

    public void setAnnotation(String annotation) {
        Annotation = annotation;
    }

    public String getRevision() {
        return Revision;
    }

    public void setRevision(String revision) {
        Revision = revision;
    }

    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String resolution) {
        Resolution = resolution;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
