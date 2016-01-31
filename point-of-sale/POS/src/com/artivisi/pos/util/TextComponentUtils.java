package com.artivisi.pos.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.artivisi.pos.ui.frame.FrameUtama;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.springframework.util.StringUtils;

/** TextComponentUtils ini adalah utility yang bertugas untuk mengatur segala
 * keperluan pada Component JTextField. Behaviour yang terdapat pada class ini
 * adalah sebagai berikut :
 *
 * @author martin
 */
public class TextComponentUtils {

    private static final String BAD_CHARS = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";

    public static void scrollToRect(JTable table,int nextSelectedRow){
        Rectangle currentVisible = table.getVisibleRect();
        Rectangle scrollToRect = table.getCellRect(nextSelectedRow, 0, true);
        if(scrollToRect.getY() > currentVisible.getY() + currentVisible.getHeight()){
            scrollToRect.setLocation(0,
                    (int)(scrollToRect.getY() + currentVisible.getHeight() - scrollToRect.getHeight()));
        }
        table.scrollRectToVisible(scrollToRect);
    }

    /** TextComponentUtils.setMaximumLength()
     */
    public static void setMaximumLength(final int maximumChar,
            final javax.swing.JTextField textField) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                limitAction(textField, evt, maximumChar);
            }
        });
    }

    public static void setAutoUpperCaseText(final int maxChar,
            final javax.swing.JTextField textField) {
        setMaximumLength(maxChar, textField);
        setAutoUpperCaseText(textField);
    }

    public static void setNumericTextOnly(final javax.swing.JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent evt) {
                if (!Character.isDigit(evt.getKeyChar()) ||
                    BAD_CHARS.indexOf(evt.getKeyChar()) > -1) {
                        evt.consume();
                    }
                }
        });
    }

    public static JTextField setCurrency(JTextField textField){
        textField.addKeyListener(new IntegerMasking());
        return textField;
    }

    public static BigDecimal parseNumberToBigDecimal(String text){
        if(!StringUtils.hasText(text)) return BigDecimal.ZERO;
        try {
            BigDecimal number = new BigDecimal(NumberFormat.getInstance().parse(text).doubleValue());
            return number;
        } catch (ParseException ex) {
            if(Locale.US == Locale.getDefault()){
                JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Regional setting anda menggunakan US. Pemisah ribuan adalah . (titlk) dan pemisah pecahan adalah , (koma)");
            } else if(Locale.getDefault().getCountry().equalsIgnoreCase("INDONESIA")
                    && Locale.getDefault().getLanguage().equalsIgnoreCase("ID")) {
                JOptionPane.showMessageDialog(FrameUtama.getInstance(), "Regional setting anda menggunakan Indonesia. Pemisah ribuan adalah , (koma) dan pemisah pecahan adalah . (titik)");
            }
        }
        return null;
    }

    public static void setAutoUpperCaseText(final javax.swing.JTextField textField) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (Character.isLetter(e.getKeyChar())) {
                    convertToUpperCase((JTextField) e.getSource());
                }
            }
        });
    }
    public static void setAutoUpperCaseText(final javax.swing.JTextArea textArea) {
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                if (Character.isLetter(e.getKeyChar())) {
                    convertToUpperCase((JTextArea) e.getSource());
                }
            }
        });
    }


    private static void limitAction(final javax.swing.JTextField textField,
            java.awt.event.KeyEvent keyEvent, int maxLength) {

        if (textField.getText().length() + 1 > maxLength) {
            keyEvent.consume();
        }
    }

    /** Method ini akan mengambil text angka saja */
    public static String getValueFromTextNumber(final JTextField text) {
        final char txt[] = text.getText().toCharArray();
        StringBuilder sb = new StringBuilder();
        String tmp = "";
        for (int i=0; i<txt.length; i++) {
            tmp = String.valueOf(txt[i]);

            if (tmp.equals(".") || tmp.equals(",")) {
            } else {
                sb.append(tmp);
            }
        }

        return sb.toString();
    }

    private static void convertToUpperCase(final javax.swing.JTextField textField) {
        textField.setText(textField.getText().toUpperCase());
    }
    private static void convertToUpperCase(final javax.swing.JTextArea textField) {
        textField.setText(textField.getText().toUpperCase());
    }

    private static class IntegerMasking implements KeyListener {

        public void keyTyped(KeyEvent evt) {
            JTextField source = (JTextField) evt.getSource();
            if(Locale.getDefault() == Locale.US){
                if (Character.isLetter(evt.getKeyChar()) ||
                        BAD_CHARS.indexOf(evt.getKeyChar()) >= 0) {
                    if(evt.getKeyChar()!='.' || source.getText().indexOf('.') >= 0){
                        evt.consume();
                    }
                }
            } else {
                if (Character.isLetter(evt.getKeyChar()) ||
                        BAD_CHARS.indexOf(evt.getKeyChar()) >= 0 ) {
                    if(evt.getKeyChar()!=',' || source.getText().indexOf(',') >= 0){
                        evt.consume();
                    }
                }

            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent evt) {
            if(evt.getKeyCode() == KeyEvent.VK_LEFT
                    || evt.getKeyCode() == KeyEvent.VK_RIGHT){
                return;
            }
            if (evt.getSource() instanceof JTextField ) {
                JTextField textField = (JTextField) evt.getSource();
                int caretPosition = textField.getCaretPosition();
                int initialLentgh = textField.getText()!=null ? textField.getText().length() : 0;
                String formatedNumber = formatNumber(textField.getText());
                textField.setText(formatedNumber);
                if(formatedNumber.length() > initialLentgh){
                    textField.setCaretPosition(caretPosition + 1);
                }
            }
        }
    }

    public static String formatNumber(BigDecimal value){
        if(value == null || value.equals(BigDecimal.ZERO)){
            return "0";
        } else {
            NumberFormat formatter = NumberFormat.getInstance();
            formatter.setMinimumFractionDigits(0);
            formatter.setMaximumFractionDigits(0);
            return formatter.format(value.setScale(0, RoundingMode.HALF_EVEN));
        }
    }

    public static String formatNumber(String text){
        char thousandsSeparator = '.';
        char decimalSeparator = ',';
        if(Locale.getDefault().equals(Locale.US)){
            thousandsSeparator = ',';
            decimalSeparator = '.';
        } else if(Locale.getDefault().getCountry().equalsIgnoreCase("INDONESIA")
                && Locale.getDefault().getLanguage().equalsIgnoreCase("ID")){
            thousandsSeparator = '.';
            decimalSeparator = ',';
        }
        StringBuilder builder = new StringBuilder();
        boolean isDecimalSeparatorFound = false;
        for (Character c : text.toCharArray()) {
            if (c != thousandsSeparator) {
                builder.append(c);
                if(c == decimalSeparator ){
                    isDecimalSeparatorFound = true;
                }
            }
        }
        char[] arr = builder.toString().toCharArray();
        StringBuilder builder1 = new StringBuilder();
        int maxIndex = arr.length - 1;
        //mengambil pecahan
        int i=0;
        int decimalSeparatorIndex = 0;
        if(isDecimalSeparatorFound){
            for(;i<=maxIndex;i++){
                char c = arr[maxIndex - i];
                if(c!=decimalSeparator){
                    builder1.append(c);
                } else {
                    isDecimalSeparatorFound = false;
                    break;
                }
            }
            builder1.append(arr[maxIndex - i++]);
            decimalSeparatorIndex = i;
        }
        for (i=0; i <= maxIndex - decimalSeparatorIndex; i++) {
            char c = arr[maxIndex - i - decimalSeparatorIndex];
            if (i != 0 && i % 3 == 0 ) {
                builder1.append(thousandsSeparator);
                builder1.append(c);
            } else {
                builder1.append(c);
            }
        }
        StringBuilder builder2 = new StringBuilder();
        char[] arr2 = builder1.toString().toCharArray();
        maxIndex = arr2.length - 1;
        for (i = 0; i <= maxIndex; i++) {
            char c = arr2[maxIndex - i];
            builder2.append(c);
        }
        return builder2.toString();
    }
}