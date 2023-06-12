import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SystemOrganizacjiImprez {
    private static ArrayList<Klient> listaKlientow = new ArrayList<>();
    private static ArrayList<Impreza> listaImprez = new ArrayList<>();
    private static HashMap<String, ArrayList<String>> rezerwacje = new HashMap<>();
    private static HashMap<String, ArrayList<String>> atrakcje = new HashMap<>();
    private static HashMap<String, ArrayList<String>> pracownicy = new HashMap<>();
    private static JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("System Organizacji Imprez");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maksymalizacja ramki na ekranie

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addClientButton = new JButton("Dodaj klienta");
        JButton removeClientButton = new JButton("Usuń klienta");
        JButton addEventButton = new JButton("Dodaj imprezę");
        JButton removeEventButton = new JButton("Usuń imprezę");
        JButton bookButton = new JButton("Zarezerwuj miejsce");
        JButton cancelBookingButton = new JButton("Anuluj rezerwację");
        JButton addAttractionButton = new JButton("Dodaj atrakcję");
        JButton addEmployeeButton = new JButton("Dodaj pracownika");
        JButton removeEmployeeButton = new JButton("Usuń pracownika");
        JButton searchButton = new JButton("Wyszukaj");

        panel.add(addClientButton);
        panel.add(removeClientButton);
        panel.add(addEventButton);
        panel.add(removeEventButton);
        panel.add(bookButton);
        panel.add(cancelBookingButton);
        panel.add(addAttractionButton);
        panel.add(addEmployeeButton);
        panel.add(removeEmployeeButton);
        panel.add(searchButton);

        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddClientFrame();
            }
        });

        removeClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRemoveClientFrame();
            }
        });

        addEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddEventFrame();
            }
        });

        removeEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRemoveEventFrame();
            }
        });

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBookFrame();
            }
        });

        cancelBookingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createCancelBookingFrame();
            }
        });

        addAttractionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddAttractionFrame();
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddEmployeeFrame();
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRemoveEmployeeFrame();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createSearchFrame();
            }
        });

        return panel;
    }

    private static void createAddClientFrame() {
        JFrame frame = new JFrame("Dodaj Klienta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Imię:");
        JLabel surnameLabel = new JLabel("Nazwisko:");
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JButton addButton = new JButton("Dodaj klienta");

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(surnameLabel);
        frame.add(surnameField);
        frame.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();

                if (name.isEmpty() || surname.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Wprowadź poprawne dane klienta.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Klient klient = new Klient(name, surname);
                listaKlientow.add(klient);

                JOptionPane.showMessageDialog(frame, "Dodano klienta: " + klient, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                surnameField.setText("");
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createRemoveClientFrame() {
        if (listaKlientow.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista klientów jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Usuń Klienta");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        JLabel clientLabel = new JLabel("Klient:");
        JComboBox<String> clientComboBox = new JComboBox<>();

        for (Klient klient : listaKlientow) {
            clientComboBox.addItem(klient.getImie() + " " + klient.getNazwisko());
        }

        JButton removeButton = new JButton("Usuń klienta");

        frame.add(clientLabel);
        frame.add(clientComboBox);
        frame.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedClient = (String) clientComboBox.getSelectedItem();

                if (selectedClient == null) {
                    JOptionPane.showMessageDialog(frame, "Wybierz klienta do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] clientData = selectedClient.split(" ");
                String name = clientData[0];
                String surname = clientData[1];

                Klient klientToRemove = null;
                for (Klient klient : listaKlientow) {
                    if (klient.getImie().equals(name) && klient.getNazwisko().equals(surname)) {
                        klientToRemove = klient;
                        break;
                    }
                }

                if (klientToRemove != null) {
                    usunRezerwacjeKlienta(klientToRemove);
                    listaKlientow.remove(klientToRemove);
                    JOptionPane.showMessageDialog(frame, "Usunięto klienta: " + selectedClient, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    clientComboBox.removeItem(selectedClient);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono klienta o podanych danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAddEventFrame() {
        JFrame frame = new JFrame("Dodaj Imprezę");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Nazwa:");
        JLabel dateLabel = new JLabel("Data:");
        JLabel placeLabel = new JLabel("Miejsce:");
        JLabel capacityLabel = new JLabel("Pojemność:");
        JLabel typeLabel = new JLabel("Rodzaj:");
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField placeField = new JTextField();
        JTextField capacityField = new JTextField();
        JTextField typeField = new JTextField();
        JButton addButton = new JButton("Dodaj imprezę");

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(dateLabel);
        frame.add(dateField);
        frame.add(placeLabel);
        frame.add(placeField);
        frame.add(capacityLabel);
        frame.add(capacityField);
        frame.add(typeLabel);
        frame.add(typeField);
        frame.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String date = dateField.getText();
                String place = placeField.getText();
                int capacity = Integer.parseInt(capacityField.getText());
                String type = typeField.getText();

                if (name.isEmpty() || date.isEmpty() || place.isEmpty() || capacityField.getText().isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Wprowadź poprawne dane imprezy.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Impreza impreza = new Impreza(name, date, place, capacity, type);
                listaImprez.add(impreza);

                JOptionPane.showMessageDialog(frame, "Dodano imprezę: " + impreza, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                dateField.setText("");
                placeField.setText("");
                capacityField.setText("");
                typeField.setText("");
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createRemoveEventFrame() {
        if (listaImprez.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista imprez jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Usuń Imprezę");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        JLabel eventLabel = new JLabel("Impreza:");
        JComboBox<String> eventComboBox = new JComboBox<>();

        for (Impreza impreza : listaImprez) {
            eventComboBox.addItem(impreza.getNazwa());
        }

        JButton removeButton = new JButton("Usuń imprezę");

        frame.add(eventLabel);
        frame.add(eventComboBox);
        frame.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = (String) eventComboBox.getSelectedItem();

                if (eventName == null) {
                    JOptionPane.showMessageDialog(frame, "Wybierz imprezę do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Impreza imprezaToRemove = null;
                for (Impreza impreza : listaImprez) {
                    if (impreza.getNazwa().equals(eventName)) {
                        imprezaToRemove = impreza;
                        break;
                    }
                }

                if (imprezaToRemove != null) {
                    usunRezerwacjeImprezy(imprezaToRemove);
                    listaImprez.remove(imprezaToRemove);
                    JOptionPane.showMessageDialog(frame, "Usunięto imprezę: " + eventName, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    eventComboBox.removeItem(eventName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono imprezy o podanej nazwie.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createBookFrame() {
        if (listaImprez.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista imprez jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (listaKlientow.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista klientów jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Zarezerwuj Miejsce");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel eventLabel = new JLabel("Nazwa imprezy:");
        JLabel clientLabel = new JLabel("Klient:");
        JComboBox<String> eventComboBox = new JComboBox<>();
        JComboBox<String> clientComboBox = new JComboBox<>();

        for (Impreza impreza : listaImprez) {
            eventComboBox.addItem(impreza.getNazwa());
        }

        for (Klient klient : listaKlientow) {
            clientComboBox.addItem(klient.getImie() + " " + klient.getNazwisko());
        }

        JButton bookButton = new JButton("Zarezerwuj miejsce");

        frame.add(eventLabel);
        frame.add(eventComboBox);
        frame.add(clientLabel);
        frame.add(clientComboBox);
        frame.add(bookButton);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = (String) eventComboBox.getSelectedItem();
                String clientName = (String) clientComboBox.getSelectedItem();

                if (eventName == null || clientName == null) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane rezerwacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!rezerwacje.containsKey(eventName)) {
                    rezerwacje.put(eventName, new ArrayList<>());
                }

                ArrayList<String> reservedClients = rezerwacje.get(eventName);
                reservedClients.add(clientName);

                JOptionPane.showMessageDialog(frame, "Zarezerwowano miejsce dla klienta: " + clientName, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                clientComboBox.removeItem(clientName);

                if (clientComboBox.getItemCount() == 0) {
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createCancelBookingFrame() {
        if (listaImprez.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista imprez jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (listaKlientow.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista klientów jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Anuluj Rezerwację");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        JLabel eventLabel = new JLabel("Nazwa imprezy:");
        JLabel clientLabel = new JLabel("Klient:");
        JComboBox<String> eventComboBox = new JComboBox<>();
        JComboBox<String> clientComboBox = new JComboBox<>();

        for (Map.Entry<String, ArrayList<String>> entry : rezerwacje.entrySet()) {
            String eventName = entry.getKey();
            ArrayList<String> reservedClients = entry.getValue();
            for (String client : reservedClients) {
                eventComboBox.addItem(eventName);
                clientComboBox.addItem(client);
            }
        }

        JButton cancelButton = new JButton("Anuluj rezerwację");

        frame.add(eventLabel);
        frame.add(eventComboBox);
        frame.add(clientLabel);
        frame.add(clientComboBox);
        frame.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = (String) eventComboBox.getSelectedItem();
                String clientName = (String) clientComboBox.getSelectedItem();

                if (eventName == null || clientName == null) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane anulowania rezerwacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!rezerwacje.containsKey(eventName)) {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono rezerwacji dla podanej imprezy.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<String> reservedClients = rezerwacje.get(eventName);
                if (reservedClients.contains(clientName)) {
                    reservedClients.remove(clientName);
                    JOptionPane.showMessageDialog(frame, "Anulowano rezerwację dla klienta: " + clientName, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    clientComboBox.removeItem(clientName);

                    if (clientComboBox.getItemCount() == 0) {
                        frame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono rezerwacji dla podanych danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAddAttractionFrame() {
        if (listaImprez.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista imprez jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Dodaj Atrakcję");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel eventLabel = new JLabel("Nazwa imprezy:");
        JLabel attractionLabel = new JLabel("Atrakcja:");
        JComboBox<String> eventComboBox = new JComboBox<>();
        JTextField attractionField = new JTextField();
        JButton addButton = new JButton("Dodaj atrakcję");

        for (Impreza impreza : listaImprez) {
            eventComboBox.addItem(impreza.getNazwa());
        }

        frame.add(eventLabel);
        frame.add(eventComboBox);
        frame.add(attractionLabel);
        frame.add(attractionField);
        frame.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = (String) eventComboBox.getSelectedItem();
                String attraction = attractionField.getText();

                if (eventName == null || attraction.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane dodawania atrakcji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!atrakcje.containsKey(eventName)) {
                    atrakcje.put(eventName, new ArrayList<>());
                }

                ArrayList<String> attractions = atrakcje.get(eventName);
                attractions.add(attraction);

                JOptionPane.showMessageDialog(frame, "Dodano atrakcję: " + attraction, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                attractionField.setText("");
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createAddEmployeeFrame() {
        JFrame frame = new JFrame("Dodaj Pracownika");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel jobLabel = new JLabel("Stanowisko:");
        JLabel employeeLabel = new JLabel("Pracownik:");
        JTextField jobField = new JTextField();
        JTextField employeeField = new JTextField();
        JButton addButton = new JButton("Dodaj pracownika");

        frame.add(jobLabel);
        frame.add(jobField);
        frame.add(employeeLabel);
        frame.add(employeeField);
        frame.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String job = jobField.getText();
                String employee = employeeField.getText();

                if (job.isEmpty() || employee.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Wprowadź poprawne dane pracownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!pracownicy.containsKey(job)) {
                    pracownicy.put(job, new ArrayList<>());
                }

                ArrayList<String> employees = pracownicy.get(job);
                employees.add(employee);

                JOptionPane.showMessageDialog(frame, "Dodano pracownika: " + employee, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                jobField.setText("");
                employeeField.setText("");
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createRemoveEmployeeFrame() {
        if (pracownicy.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lista pracowników jest pusta.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Usuń Pracownika");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        JLabel jobLabel = new JLabel("Stanowisko:");
        JLabel employeeLabel = new JLabel("Pracownik:");
        JComboBox<String> jobComboBox = new JComboBox<>();

        for (Map.Entry<String, ArrayList<String>> entry : pracownicy.entrySet()) {
            String job = entry.getKey();
            jobComboBox.addItem(job);
        }

        JComboBox<String> employeeComboBox = new JComboBox<>();
        JButton removeButton = new JButton("Usuń pracownika");

        frame.add(jobLabel);
        frame.add(jobComboBox);
        frame.add(employeeLabel);
        frame.add(employeeComboBox);
        frame.add(removeButton);

        jobComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedJob = (String) jobComboBox.getSelectedItem();
                if (selectedJob != null) {
                    employeeComboBox.removeAllItems();
                    ArrayList<String> employees = pracownicy.get(selectedJob);
                    for (String employee : employees) {
                        employeeComboBox.addItem(employee);
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedJob = (String) jobComboBox.getSelectedItem();
                String selectedEmployee = (String) employeeComboBox.getSelectedItem();

                if (selectedJob == null || selectedEmployee == null) {
                    JOptionPane.showMessageDialog(frame, "Wybierz pracownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<String> employees = pracownicy.get(selectedJob);
                if (employees.contains(selectedEmployee)) {
                    employees.remove(selectedEmployee);
                    JOptionPane.showMessageDialog(frame, "Usunięto pracownika: " + selectedEmployee, "Sukces", JOptionPane.INFORMATION_MESSAGE);
                    employeeComboBox.removeItem(selectedEmployee);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono pracownika o podanych danych.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private static void createSearchFrame() {
        JFrame frame = new JFrame("Wyszukaj");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        JLabel searchTypeLabel = new JLabel("Typ wyszukiwania:");
        JComboBox<String> searchTypeComboBox = new JComboBox<>();
        searchTypeComboBox.addItem("Osoba");
        searchTypeComboBox.addItem("Atrakcja");
        searchTypeComboBox.addItem("Pracownik");
        searchTypeComboBox.addItem("Impreza");
        JLabel keywordLabel = new JLabel("Słowo kluczowe:");
        JTextField keywordField = new JTextField();
        JButton searchButton = new JButton("Szukaj");

        frame.add(searchTypeLabel);
        frame.add(searchTypeComboBox);
        frame.add(keywordLabel);
        frame.add(keywordField);
        frame.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchType = (String) searchTypeComboBox.getSelectedItem();
                String keyword = keywordField.getText();

                if (searchType == null || keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Niepoprawne dane wyszukiwania.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder results = new StringBuilder();
                if (searchType.equals("Osoba")) {
                    for (Klient klient : listaKlientow) {
                        String klientData = klient.toString();
                        if (klientData.toLowerCase().contains(keyword.toLowerCase())) {
                            results.append(klientData).append("\n");
                        }
                    }
                } else if (searchType.equals("Impreza")) {
                    for (Impreza impreza : listaImprez) {
                        String imprezaData = impreza.toString();
                        if (imprezaData.toLowerCase().contains(keyword.toLowerCase())) {
                            results.append(imprezaData).append("\n");
                        }
                    }
                } else if (searchType.equals("Atrakcja")) {
                    for (Map.Entry<String, ArrayList<String>> entry : atrakcje.entrySet()) {
                        String eventName = entry.getKey();
                        ArrayList<String> attractions = entry.getValue();
                        for (String attraction : attractions) {
                            if (attraction.toLowerCase().contains(keyword.toLowerCase())) {
                                results.append("Impreza: ").append(eventName).append(", Atrakcja: ").append(attraction).append("\n");
                            }
                        }
                    }
                } else if (searchType.equals("Pracownik")) {
                    for (Map.Entry<String, ArrayList<String>> entry : pracownicy.entrySet()) {
                        String job = entry.getKey();
                        ArrayList<String> employees = entry.getValue();
                        for (String employee : employees) {
                            if (employee.toLowerCase().contains(keyword.toLowerCase())) {
                                results.append("Stanowisko: ").append(job).append(", Pracownik: ").append(employee).append("\n");
                            }
                        }
                    }
                }

                if (results.length() > 0) {
                    updateTextArea("Wyniki wyszukiwania dla '" + keyword + "':\n" + results.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Brak wyników dla podanych danych.", "Brak wyników", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton saveButton = new JButton("Zapisz do pliku");
        JButton loadButton = new JButton("Wczytaj z pliku");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(panel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    saveDataToFile(file);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(panel);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    loadDataFromFile(file);
                }
            }
        });

        return panel;
    }

    private static void updateTextArea(String message) {
        textArea.append(message + "\n");
    }

    private static void usunRezerwacjeKlienta(Klient klient) {
        for (Map.Entry<String, ArrayList<String>> entry : rezerwacje.entrySet()) {
            ArrayList<String> reservedClients = entry.getValue();
            reservedClients.remove(klient.getImie() + " " + klient.getNazwisko());
        }
    }

    private static void usunRezerwacjeImprezy(Impreza impreza) {
        rezerwacje.remove(impreza.getNazwa());
    }

    private static void saveDataToFile(File file) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(listaKlientow);
            outputStream.writeObject(listaImprez);
            outputStream.writeObject(rezerwacje);
            outputStream.writeObject(atrakcje);
            outputStream.writeObject(pracownicy);
            outputStream.close();

            JOptionPane.showMessageDialog(null, "Dane zapisano do pliku.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas zapisu danych do pliku.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void loadDataFromFile(File file) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            listaKlientow = (ArrayList<Klient>) inputStream.readObject();
            listaImprez = (ArrayList<Impreza>) inputStream.readObject();
            rezerwacje = (HashMap<String, ArrayList<String>>) inputStream.readObject();
            atrakcje = (HashMap<String, ArrayList<String>>) inputStream.readObject();
            pracownicy = (HashMap<String, ArrayList<String>>) inputStream.readObject();
            inputStream.close();

            JOptionPane.showMessageDialog(null, "Dane wczytano z pliku.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wczytywania danych z pliku.", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Nieprawidłowy format pliku.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class Klient implements Serializable {
    private String imie;
    private String nazwisko;

    public Klient(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko;
    }
}

class Impreza implements Serializable {
    private String nazwa;
    private String data;
    private String miejsce;
    private int pojemnosc;
    private String rodzaj;

    public Impreza(String nazwa, String data, String miejsce, int pojemnosc, String rodzaj) {
        this.nazwa = nazwa;
        this.data = data;
        this.miejsce = miejsce;
        this.pojemnosc = pojemnosc;
        this.rodzaj = rodzaj;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getData() {
        return data;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    @Override
    public String toString() {
        return nazwa + " (" + data + ") - " + miejsce + " - " + rodzaj;
    }
}
