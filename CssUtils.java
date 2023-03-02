import org.apache.commons.lang3.StringUtils;

/**
 * @author JoeZepernick
 * created on 2/22/2023
 */
public class CssUtils {

    /**
     * Un-minifies the input CSS string by adding indentation and line breaks. Also adds a new line after each comment.
     * @param css the CSS string to un-minify.
     * @return the un-minified and formatted CSS string, or the original CSS string if un-minification fails.
     */
    public static String unminify(String css) {

        // Test the CSS String and return
        // an empty String if null or blank
        if (StringUtils.isBlank(css)) {
            return "";
        }

        // initialize a StringBuilder to hold the unminified CSS string
        StringBuilder unminified = new StringBuilder();

        // initialize a counter for indentation
        int indent = 0;

        // initialize a boolean flag to keep track of whether we are currently in a CSS comment
        boolean inComment = false;

        // loop through each character in the input CSS string
        for (int i = 0; i < css.length(); i++) {
            char c = css.charAt(i);

            // check if the current character is a forward slash
            switch (c) {
                case '/':
                    // check if the next character is an asterisk, indicating the start of a comment
                    if (i < css.length() - 1 && css.charAt(i + 1) == '*') {
                        // add the opening comment tag
                        unminified.append("/*");
                        // set the inComment flag to true
                        inComment = true;
                        i++;
                    }
                    // check if we are currently in a comment and the next character is a forward slash, indicating the end of a single-line comment
                    else if (inComment && i < css.length() - 1 && css.charAt(i + 1) == '/') {
                        // set the inComment flag to false
                        inComment = false;
                        // add a line break and indentation after the comment
                        unminified.append(c).append('\n').append(getIndent(indent));
                        i++;
                    }
                    // if none of the above conditions are true, just append the forward slash to the unminified string
                    else {
                        unminified.append(c);
                    }
                    break;

                // check if the current character is an asterisk
                case '*':
                    // check if we are currently in a comment and the next character is a forward slash, indicating the end of a comment
                    if (inComment && i < css.length() - 1 && css.charAt(i + 1) == '/') {
                        // add the closing comment tag and a line break after the comment
                        unminified.append("*/\n");
                        // set the inComment flag to false
                        inComment = false;
                        i++;
                        // append indentation after the comment
                        unminified.append(getIndent(indent));
                    }
                    // if none of the above conditions are true, just append the asterisk to the unminified string
                    else {
                        unminified.append(c);
                    }
                    break;

                // check if the current character is an opening curly brace
                case '{':
                    // add the opening brace to the unminified string, followed by a line break and incremented indentation
                    unminified.append(" {\n");
                    indent++;
                    unminified.append(getIndent(indent));
                    break;

                // check if the current character is a closing curly brace
                case '}':
                    // add a line break and decremented indentation before the closing brace, followed by a line break and indentation after the brace
                    unminified.append("\n");
                    indent--;
                    unminified.append(getIndent(indent));
                    unminified.append("}\n");
                    unminified.append(getIndent(indent));
                    break;

                // check if the current character is a semicolon
                case ';':
                    // add the semicolon to the unminified string, followed by indentation
                    unminified.append(";\n");
                    unminified.append(getIndent(indent));
                    break;
                // if the current character is not a comment, brace, or semicolon, just append it to the unminified string
                default:
                    unminified.append(c);
                    break;
            }
        }

        return unminified.toString();
    }

    /**
     * Returns a string containing the specified number of spaces to use for indentation.
     * @param indent the number of spaces to indent.
     * @return a string containing the specified number of spaces.
     */
    private static String getIndent(int indent) {
        return "    ".repeat(Math.max(0, indent));
    }
}
