package translation;

import javax.swing.*;
import java.awt.event.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            JTextField countryField = new JTextField(10);
//            countryField.setText("can");
//            countryField.setEditable(false); // we only support the "can" country code for now
//            countryPanel.add(new JLabel("Country:"));
//            countryPanel.add(countryField);

            JPanel languagePanel = new JPanel();
            LanguageCodeConverter languageObject = new LanguageCodeConverter(); // create an object which can change code to their name and name to their code
            String[] languages = languageObject.languages(); // extracts all languages

            CountryCodeConverter countryCodeObject = new CountryCodeConverter();
            String[] countries = countryCodeObject.countries();
            JPanel countryPanel = new JPanel();
            JComboBox<String> countryComboBox = new JComboBox<>();
            for (String country : countries) {
                countryComboBox.addItem(country);
            }
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryComboBox);

            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String language : languages) { // takes the language from the languages extracted
                languageComboBox.addItem(language); // add the languages to the language combo box
            };

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            // adding listener for when the user changes the language from the languageComboBox
            ActionListener updateTranslation = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageComboBox.getSelectedItem().toString(); // returns the current selected language as a string
//                    String country = countryField.getText();
//                    countryField String country = countryComboBox.getSelectedItem().toString();
                    String country = countryComboBox.getSelectedItem().toString(); // returns the current selected country as a string
                    Translator translator = new JSONTranslator();
                    CountryCodeConverter countryObject = new CountryCodeConverter();
                    LanguageCodeConverter languageObject = new LanguageCodeConverter();
                    String result = translator.translate(countryObject.fromCountry(country), languageObject.fromLanguage(language));
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            };

            // Attach same listener to both
            languageComboBox.addActionListener(updateTranslation);
            countryComboBox.addActionListener(updateTranslation);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
