
package com.baidu.navonline.parsereview;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <br/>
 * ParseReview-com.baidu.navonline.parsereview.ReviewParser.java
 * 解析code review结果
 * @author <a href="mailto:zhanghanguo@baidu.com">zhanghanguo@baidu.com</a>
 * @version 2013-1-22下午1:54:24
 */
public class ReviewParser {

    private static final String FORMAT = "问题编号：%1$d\n问题描述：%2$s:%3$s\n文件：%4$s\n行号：%5$s\n修复人：%6$s\n";

    private static final String INPUT_TOP_DIR = "input/";

    private static final String OUTPUT_TOP_DIR = "output/";

    private static final String SUFFIX_OUTPUT_FILE = ".txt";

    /**
     * @param args
     */
    public static void main(String[] args) {
        XStream stream = new XStream();
        stream.processAnnotations(Review.class);
        File file = new File(INPUT_TOP_DIR);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                Review review = (Review) stream.fromXML(f);
                int size = review.getReviewIssues().size();
                StringBuilder builder = new StringBuilder();
                builder.append("-------------- ").append(review.getId())
                        .append(" -----------").append("\n");
                for (int i = 0; i < size; i++) {
                    ReviewIssue issue = review.getReviewIssues().get(i);
                    builder.append(String.format(FORMAT, i + 1, issue
                            .getSummary(), issue.getDescription(), issue
                            .getFileTag().getContent(), issue.getFileTag()
                            .getLine(), issue.getAssignedTo()));
                    builder.append("----------------------------\n");
                }
                try {
                    FileWriter writer = new FileWriter(OUTPUT_TOP_DIR
                            + new File(f.getName() + SUFFIX_OUTPUT_FILE));
                    writer.write(builder.toString());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
