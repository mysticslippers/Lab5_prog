package me.ifmo.server.utils;

import me.ifmo.common.data.*;
import me.ifmo.common.utils.UserInputManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.DuplicateHeaderMode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for implementing the connection between a file and a collection.
 */

public class FileManager {

    /**
     * A method that implements reading data from a CSV file using a regular expression into a collection and checking it for validity.
     * @param filePath - The path to the file.
     * @return Returns a collection of objects from a file.
     */

    public static LinkedHashSet<Dragon> readFileCSVByPattern(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        ArrayList<String> infoDragon = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b([0-9а-яА-Яa-zA-Z-;,\\\\!.:_]+)\\b,");
        String line;

        if(UserInputManager.isFileNotNull() && filePath.contains("CSV")){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                bufferedReader.readLine();

                while((line = bufferedReader.readLine()) != null){
                    Color color;
                    DragonType dragonType = null;
                    DragonCharacter dragonCharacter;
                    int idCounter = Math.abs(line.hashCode());
                    Matcher matcher = pattern.matcher(line);

                    while(matcher.find()){
                        String attribute = matcher.group().trim();
                        infoDragon.add(attribute.substring(0, attribute.length() - 1));
                    }
                    infoDragon.add(line.substring(line.lastIndexOf(",") + 1).trim());

                    try{
                        if(UserInputManager.isDragonDataValid(infoDragon)){
                            color = Color.valueOf(infoDragon.get(6));
                            if(!infoDragon.get(7).isEmpty() && !infoDragon.get(7).equals("null")) dragonType = DragonType.valueOf(infoDragon.get(7));
                            dragonCharacter = DragonCharacter.valueOf(infoDragon.get(8));
                            System.out.println("Object with ID: " + infoDragon.get(0) + " added to the collection!");
                            System.out.println("Object's ID now is " + (idCounter));
                            collection.add(new Dragon(idCounter, infoDragon.get(1), new Coordinates(Long.parseLong(infoDragon.get(2)), Float.parseFloat(infoDragon.get(3))),
                                    LocalDate.now(), Long.parseLong(infoDragon.get(5)), color, dragonType, dragonCharacter, new DragonCave(Long.parseLong(infoDragon.get(9)))));
                        } else{
                            System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                            System.out.println(infoDragon);
                        }
                        infoDragon.clear();
                    }catch(IllegalArgumentException exception){
                        System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                        infoDragon.clear();
                    }
                }

            }catch(IOException exception){
                System.out.println("----------------------");
                System.out.println("The file can't be read!");
            }catch(NoSuchElementException exception){
                System.out.println("----------------------");
                System.out.println("The file is empty!");
            }
        }
        return collection;
    }

    /**
     * A method that implements reading data from a file using CSVFormat into a collection and checking it for validity.
     * @param filePath - The path to the file.
     * @return Returns a collection of objects from a file.
     */

    public static LinkedHashSet<Dragon> readFileCSVByCSVFormat(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setAutoFlush(true)
                .setDelimiter(",")
                .setDuplicateHeaderMode(DuplicateHeaderMode.ALLOW_ALL)
                .setHeader(TableDataHeaders.class)
                .setIgnoreEmptyLines(true)
                .setIgnoreSurroundingSpaces(true)
                .setNullString("null")
                .setQuote('"')
                .setRecordSeparator("\r\n")
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build();


        if(UserInputManager.isFileNotNull() && filePath.contains("CSV")){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                CSVParser csvParser = new CSVParser(bufferedReader, csvFormat);

                for(CSVRecord record : csvParser){
                    Color color;
                    DragonType type = null;
                    DragonCharacter character;
                    int idCounter = Math.abs(record.hashCode());
                    String[] object = {record.get(TableDataHeaders.id), record.get(TableDataHeaders.name), record.get(TableDataHeaders.coordinateX),
                            record.get(TableDataHeaders.coordinateY), record.get(TableDataHeaders.localDate),
                            record.get(TableDataHeaders.age), record.get(TableDataHeaders.color), record.get(TableDataHeaders.type),
                            record.get(TableDataHeaders.character), record.get(TableDataHeaders.numberOfTreasures)};

                    ArrayList<String> dragonInfo = new ArrayList<>(Arrays.asList(object));
                    if(UserInputManager.isDragonDataValid(dragonInfo)){

                        color = Color.valueOf(object[6]);
                        if(object[7] != null && !object[7].isEmpty() && !object[7].equals("null")) type = DragonType.valueOf(object[7]);
                        character = DragonCharacter.valueOf(object[8]);
                        System.out.println("Object with ID: " + object[0] + " added to the collection!");
                        System.out.println("Object's ID now is " + (idCounter));
                        collection.add(new Dragon.Builder().setId(idCounter).setName(object[1])
                                .setCoordinates(new Coordinates(Long.parseLong(object[2]), Float.parseFloat(object[3])))
                                .setCreationDate(LocalDate.now())
                                .setAge(Long.parseLong(object[5]))
                                .setColor(color)
                                .setType(type)
                                .setCharacter(character)
                                .setCave(new DragonCave(Long.parseLong(object[9])))
                                .build());
                    }
                    else{
                        System.out.println("Object data ID: " + object[0] + " is not valid!");
                    }
                }

            }catch(IOException exception){
                System.out.println("----------------------");
                System.out.println("The file can't be read!");
            }catch(NoSuchElementException exception){
                System.out.println("----------------------");
                System.out.println("The file is empty!");
            }
        }
        return collection;
    }

    /**
     * A method that implements writing collection objects by pattern to a CSV file.
     * @param collection - Original collection.
     * @param filePath - The path to the file.
     */

    public static void writeFileCSVByPattern(LinkedHashSet<? extends Dragon> collection, String filePath){
        ArrayList<String> info = new ArrayList<>();
        Pattern patternAttribute = Pattern.compile("-\\s[0-9а-яА-Яa-zA-Z-;\\\\!.:_]*");
        String line;

        if(UserInputManager.isFileNotNull() && filePath.contains("CSV")) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

                for (TableDataHeaders header : TableDataHeaders.values()) {
                    if (header.name().contains("numberOfTreasures")) bufferedWriter.write(header.name());
                    else bufferedWriter.write(header.name() + ", ");
                }
                bufferedWriter.newLine();
                bufferedWriter.flush();

                for (Dragon dragon : collection) {
                    StringBuilder record = new StringBuilder();
                    line = dragon.toString();
                    Matcher matcher = patternAttribute.matcher(line);

                    while (matcher.find()) {
                        String attribute = matcher.group().trim();
                        info.add(attribute.substring(1));
                    }

                    for (int i = 0; i < info.size(); i++) {
                        if (i == 9) {
                            record.append(info.get(i));
                        } else {
                            record.append(info.get(i)).append(", ");
                        }
                    }
                    System.out.println("Object with ID: " + info.get(0) + " written to file!");
                    bufferedWriter.write(String.valueOf(record));
                    bufferedWriter.newLine();
                    info.clear();
                    bufferedWriter.flush();
                }
            } catch (IOException exception) {
                System.out.println("----------------------");
                System.out.println("File can't be written!");
            }
        }
    }

    /**
     * A method that implements reading data from an XML file using a regular expression into a collection and checking it for validity.
     * @param filePath - The path to the file.
     * @return Returns a collection of objects from a file.
     */

    public static LinkedHashSet<Dragon> readFileXMLByPattern(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        ArrayList<String> infoDragon = new ArrayList<>();
        Pattern pattern = Pattern.compile("<.*?>(.*)<.*?>");
        String line;

        if(UserInputManager.isFileNotNull() && filePath.contains("XML")){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                bufferedReader.readLine();
                int idCounter = 0;

                while((line = bufferedReader.readLine()) != null){
                    Matcher matcher = pattern.matcher(line);
                    idCounter += Math.abs(line.hashCode() / 128);
                    if(matcher.find()){
                        String attribute = matcher.group().trim();
                        infoDragon.add(attribute.substring(attribute.indexOf(">") + 1, attribute.lastIndexOf("<")));
                    }
                    if(line.contains("</Dragon>")){
                        try {
                            if (UserInputManager.isDragonDataValid(infoDragon)){
                                Color color = Color.valueOf(infoDragon.get(6));
                                DragonType type = (!infoDragon.get(7).isEmpty() && !infoDragon.get(7).equals("null")) ? DragonType.valueOf(infoDragon.get(7)) : null;
                                DragonCharacter character = DragonCharacter.valueOf(infoDragon.get(8));
                                System.out.println("Object with ID: " + infoDragon.get(0) + " added to the collection!");
                                System.out.println("Object's ID now is " + (idCounter));
                                collection.add(new Dragon(idCounter, infoDragon.get(1), new Coordinates(Long.parseLong(infoDragon.get(2)), Float.parseFloat(infoDragon.get(3))),
                                        LocalDate.now(), Long.parseLong(infoDragon.get(5)), color, type, character, new DragonCave(Long.parseLong(infoDragon.get(9)))));
                            } else {
                                System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                                System.out.println(infoDragon);
                            }
                            infoDragon.clear();
                        }catch(IllegalArgumentException exception){
                            System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                            infoDragon.clear();
                        }
                    }
                }
            }catch(IOException exception){
                System.out.println("----------------------");
                System.out.println("The file can't be read!");
            }catch(NoSuchElementException exception){
                System.out.println("----------------------");
                System.out.println("The file is empty!");
            }
        }
        return collection;
    }

    /**
     * A method that implements reading data from an XML file using a DOM into a collection and checking it for validity.
     * @param filePath - The path to the file.
     * @return Returns a collection of objects from a file.
     */

    public static LinkedHashSet<Dragon> readFileXMLByDOM(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        ArrayList<String> dragonInfo = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document;

        if(UserInputManager.isFileNotNull() && filePath.contains("XML")){
            try{
                document = documentBuilderFactory.newDocumentBuilder().parse(new File(filePath));
                Node rootTagCollection = document.getFirstChild();
                NodeList dragonsTagsList = rootTagCollection.getChildNodes();

                for(int i = 0; i < dragonsTagsList.getLength(); i++) {
                    if (dragonsTagsList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    int idCounter = Math.abs(dragonsTagsList.item(i).hashCode());
                    NodeList dragonsAttributes = dragonsTagsList.item(i).getChildNodes();
                    for (int j = 0; j < dragonsAttributes.getLength(); j++) {
                        if (dragonsAttributes.item(j).getNodeType() != Node.ELEMENT_NODE) {
                            continue;
                        }
                        if (dragonsAttributes.item(j).getChildNodes().getLength() == 1) {
                            dragonInfo.add(dragonsAttributes.item(j).getTextContent().trim());
                        } else {
                            NodeList subAttributes = dragonsAttributes.item(j).getChildNodes();
                            for (int k = 0; k < subAttributes.getLength(); k++) {
                                if (subAttributes.item(k).getNodeType() != Node.ELEMENT_NODE) {
                                    continue;
                                }
                                dragonInfo.add(subAttributes.item(k).getTextContent().trim());
                            }
                        }
                    }
                    try {
                        Color color;
                        DragonType type;
                        DragonCharacter character;
                        if(dragonInfo.size() == 9){
                            dragonInfo.add(7, "null");
                        }
                        if (UserInputManager.isDragonDataValid(dragonInfo)) {
                            color = Color.valueOf(dragonInfo.get(6));
                            type = (!dragonInfo.get(7).isEmpty() && !dragonInfo.get(7).equals("null")) ? DragonType.valueOf(dragonInfo.get(7)) : null;
                            character = DragonCharacter.valueOf(dragonInfo.get(8));
                            collection.add(new Dragon.Builder().setId(idCounter).setName(dragonInfo.get(1))
                                    .setCoordinates(new Coordinates(Long.parseLong(dragonInfo.get(2)), Float.parseFloat(dragonInfo.get(3))))
                                    .setCreationDate(LocalDate.now()).setAge(Long.parseLong(dragonInfo.get(5)))
                                    .setColor(color).setType(type).setCharacter(character).setCave(new DragonCave(Long.parseLong(dragonInfo.get(9)))).build());
                            System.out.println("Object with ID: " + dragonInfo.get(0) + " added to the collection!");
                            System.out.println("Object's ID now is " + (idCounter));
                        } else{
                            System.out.println("Object data ID: " + dragonInfo.get(0) + " is not valid!");
                            System.out.println(dragonInfo);
                        }
                        dragonInfo.clear();
                    }catch(IllegalArgumentException exception){
                        System.out.println("Object data ID: " + dragonInfo.get(0) + " is not valid!");
                        dragonInfo.clear();
                    }
                }
            }catch (IOException | ParserConfigurationException | SAXException exception){
                System.out.println("----------------------");
                System.out.println("The file can't be read!");
            }
        }
        return collection;
    }

    /**
     * A method that implements writing collection objects by STaX to XML file.
     * @param collection - Original collection.
     * @param filePath - The path to the file.
     */

    public static void writeFileXMLBySTaX(LinkedHashSet<? extends Dragon> collection, String filePath){
        if(UserInputManager.isFileNotNull() && filePath.contains("XML")){
            try(ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))){
                writeXML(os, collection);
                String outFile = os.toString(StandardCharsets.UTF_8);
                String formattedFile = formatXML(outFile);
                bufferedWriter.write(formattedFile);
                bufferedWriter.flush();
            }catch (IOException | XMLStreamException | TransformerException exception){
                System.out.println("----------------------");
                System.out.println("File can't be written!");
            }
        }
    }

    /**
     * A helper method for writing each collection object to XML format.
     * @param out - A byte stream where all information is written.
     * @param collection - Original collection.
     * @throws XMLStreamException - When using STaX libraries.
     */

    private static void writeXML(OutputStream out, LinkedHashSet<? extends Dragon> collection) throws XMLStreamException{
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(out);
        writer.writeStartDocument("1.0");
        writer.writeStartElement("CollectionXML");

        for(Dragon dragon : collection){
            writer.writeStartElement("Dragon");

            writer.writeStartElement("id");
            writer.writeCharacters(String.valueOf(dragon.getId()));
            writer.writeEndElement();

            writer.writeStartElement("name");
            writer.writeCharacters(dragon.getName());
            writer.writeEndElement();

            writer.writeStartElement("coordinates");
            writer.writeStartElement("coordinateX");
            writer.writeCharacters(String.valueOf(dragon.getCoordinates().getX()));
            writer.writeEndElement();
            writer.writeStartElement("coordinateY");
            writer.writeCharacters(String.valueOf(dragon.getCoordinates().getY()));
            writer.writeEndElement();
            writer.writeEndElement();

            writer.writeStartElement("localDate");
            writer.writeCharacters(String.valueOf(dragon.getCreationDate()));
            writer.writeEndElement();

            writer.writeStartElement("age");
            writer.writeCharacters(String.valueOf(dragon.getAge()));
            writer.writeEndElement();

            writer.writeStartElement("color");
            writer.writeCharacters(String.valueOf(dragon.getColor()));
            writer.writeEndElement();

            writer.writeStartElement("type");
            writer.writeCharacters(String.valueOf(dragon.getType()));
            writer.writeEndElement();

            writer.writeStartElement("character");
            writer.writeCharacters(String.valueOf(dragon.getCharacter()));
            writer.writeEndElement();

            writer.writeStartElement("cave");
            writer.writeStartElement("numberOfTreasures");
            writer.writeCharacters(String.valueOf(dragon.getCave().getNumberOfTreasures()));
            writer.writeEndElement();
            writer.writeEndElement();

            writer.writeEndElement();
            System.out.println("Object with ID: " + dragon.getId() + " written to file!");
        }
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }

    /**
     * A helper method for formatting a raw XML file into a nice look.
     * @param rowFile - Source XML file.
     * @return - Returns the formatted XML file so far as a string.
     * @throws TransformerException - When using STaX libraries.
     */

    private static String formatXML(String rowFile) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        StreamSource source = new StreamSource(new StringReader(rowFile));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));
        return output.toString();
    }

    /**
     * A method that implements writing collection objects by Pattern to JSON file.
     * @param filePath - The path to the file.
     * @return Returns a collection of objects from a file.
     */

    public static LinkedHashSet<Dragon> readFileJSONByPattern(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        ArrayList<String> infoDragon = new ArrayList<>();
        Pattern pattern = Pattern.compile("\".*\": \"?([-0-9a-zA-Z]*)[^{\\[]\"?\\b");
        String line;

        if(UserInputManager.isFileNotNull() && filePath.contains("JSON")){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                Color color;
                DragonType type;
                DragonCharacter character;
                while((line = bufferedReader.readLine()) != null){
                    Matcher matcher = pattern.matcher(line);
                    int idCounter = Math.abs(line.hashCode());
                    if(matcher.find()){
                        String attribute = matcher.group().trim();
                        infoDragon.add(attribute.substring(attribute.lastIndexOf(":") + 2).replace("\"", ""));
                    }
                    if(line.contains("numberOfTreasures")){
                        try {
                            if(UserInputManager.isDragonDataValid(infoDragon)){
                                color = Color.valueOf(infoDragon.get(6));
                                type = (!infoDragon.get(7).isEmpty() && !infoDragon.get(7).equals("null")) ? DragonType.valueOf(infoDragon.get(7)) : null;
                                character = DragonCharacter.valueOf(infoDragon.get(8));
                                System.out.println("Object with ID: " + infoDragon.get(0) + " added to the collection!");
                                System.out.println("Object's ID now is " + (idCounter));
                                collection.add(new Dragon(idCounter, infoDragon.get(1), new Coordinates(Long.parseLong(infoDragon.get(2)), Float.parseFloat(infoDragon.get(3))),
                                        LocalDate.now(), Long.parseLong(infoDragon.get(5)), color, type, character, new DragonCave(Long.parseLong(infoDragon.get(9)))));
                            } else {
                                System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                                System.out.println(infoDragon);
                            }
                            infoDragon.clear();
                        } catch (IllegalArgumentException exception){
                            System.out.println("Object data ID: " + infoDragon.get(0) + " is not valid!");
                            infoDragon.clear();
                        }
                    }
                }
            }catch (IOException exception){
                System.out.println("----------------------");
                System.out.println("The file can't be read!");
            } catch(NoSuchElementException exception){
                System.out.println("----------------------");
                System.out.println("The file is empty!");
            }
        }
        return collection;
    }

    public static LinkedHashSet<Dragon> readFileJSONByLibrary(String filePath){
        LinkedHashSet<Dragon> collection = new LinkedHashSet<>();
        return collection;
    }

    public static void writeFileJSONByLibrary(LinkedHashSet<Dragon> collection, String filePath){}
}
