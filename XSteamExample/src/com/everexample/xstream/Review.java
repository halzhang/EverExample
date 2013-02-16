
package com.everexample.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>
 * ParseReview-com.baidu.navonline.parsereview.Review.java
 * 
 * @author <a href="mailto:zhanghanguo@baidu.com">zhanghanguo@baidu.com</a>
 * @version 2013-1-22下午1:31:10
 */
@XStreamAlias("Review")
public class Review {
    
    @XStreamAsAttribute
    @XStreamAlias("id")
    private String id;

    @XStreamImplicit(itemFieldName = "ReviewIssue")
    private List<ReviewIssue> reviewIssues;
    
    public Review(ReviewIssue... issues) {
        this.reviewIssues = Arrays.asList(issues);
    }

    public List<ReviewIssue> getReviewIssues() {
        return reviewIssues;
    }

    public void setReviewIssues(List<ReviewIssue> reviewIssues) {
        this.reviewIssues = reviewIssues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

}
