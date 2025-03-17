package cc.yiueil.util;

/**
 * HtmlUtils html工具类
 * v1.0: 实现链式构建一个 html 文本内容
 * // todo 增加addElement的方式来实现html内容构建
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/20 17:32
 */
public class HtmlUtils {
    private final StringBuilder HTML_BUILDER;

    public static HtmlUtils create() {
        return new HtmlUtils();
    }

    private HtmlUtils() {
        this.HTML_BUILDER = new StringBuilder();
    }

    public HtmlUtils addHeading(int level, String text) {
        final int max = 6;
        if (level > max) {
            throw new IllegalArgumentException("Invalid heading level: " + level);
        }
        HTML_BUILDER.append("<h").append(level).append(">").append(text).append("</h").append(level).append(">");
        return this;
    }

    public HtmlUtils addText(String text) {
        HTML_BUILDER.append(text);
        return this;
    }

    public HtmlUtils addBoldText(String text) {
        HTML_BUILDER.append("<b>").append(text).append("</b>");
        return this;
    }

    public HtmlUtils addItalicText(String text) {
        HTML_BUILDER.append("<i>").append(text).append("</i>");
        return this;
    }

    public HtmlUtils addLink(String url, String text) {
        HTML_BUILDER.append("<a href=\"").append(url).append("\">").append(text).append("</a>");
        return this;
    }

    public HtmlUtils addImage(String url, String alt) {
        HTML_BUILDER.append("<img src=\"").append(url).append("\" alt=\"").append(alt).append("\">");
        return this;
    }

    public HtmlUtils addLineBreak() {
        HTML_BUILDER.append("<br/>");
        return this;
    }

    public String build() {
        return HTML_BUILDER.toString();
    }
}