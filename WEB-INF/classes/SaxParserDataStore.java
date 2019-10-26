
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API.
SAX was the first widely adopted API for XML in Java, and is a �de facto� standard.
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java.

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.nio.file.*;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure.
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
    TV tv;
    SoundSystem soundsystems;
    Phone phone;
    Laptop laptop;
    Fitness fitness;
    Smart smart;
    Headphones headphones;
    Wireless wireless;
    Voice voice;
    Accessory accessory;
    static HashMap<String,TV> tvs;
    static HashMap<String,SoundSystem> soundSystems;
    static HashMap<String,Phone> phones;
    static HashMap<String,Laptop> laptops;
    static HashMap<String,Fitness> fitnesses;
    static HashMap<String,Smart> smarts;
    static HashMap<String,Headphones> headphoness;
    static HashMap<String,Wireless> wirelesss;
    static HashMap<String,Voice> voices;
    static HashMap<String,Accessory> accessories;
    String consoleXmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
    public SaxParserDataStore()
	{
	}
	public SaxParserDataStore(String consoleXmlFileName) {
	    this.consoleXmlFileName = consoleXmlFileName;
	    tvs = new HashMap<String,TV>();
	    soundSystems = new HashMap<String,SoundSystem>();
	    phones = new HashMap<String,Phone>();
	    laptops = new HashMap<String,Laptop>();
	    fitnesses = new HashMap<String,Fitness>();
	    smarts = new HashMap<String,Smart>();
	    headphoness = new HashMap<String,Headphones>();
	    wirelesss = new HashMap<String,Wireless>();
	    voices = new HashMap<String,Voice>();
	    accessories = new HashMap<String,Accessory>();
	    accessoryHashMap = new HashMap<String,String>();
		parseDocument();
    }

   //parse the xml using sax parser to get the data
    private void parseDocument()
	{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try
		{
	    SAXParser parser = factory.newSAXParser();
	    parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}



////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document.
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////

	// when xml start element is parsed store the id into respective hashmap for console,games etc
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("tv")){
			currentElement="tv";
			tv = new TV();
			tv.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("soundsystems")){
			currentElement="soundsystems";
			soundsystems = new SoundSystem();
			soundsystems.setId(attributes.getValue("id"));
		}		
		if (elementName.equalsIgnoreCase("phone")){
			currentElement="phone";
			phone = new Phone();
			phone.setId(attributes.getValue("id"));
		}				
		if (elementName.equalsIgnoreCase("laptop")){
			currentElement="laptop";
			laptop = new Laptop();
			laptop.setId(attributes.getValue("id"));
		}				
		if (elementName.equalsIgnoreCase("fitness")){
			currentElement="fitness";
			fitness = new Fitness();
			fitness.setId(attributes.getValue("id"));
		}						
		if (elementName.equalsIgnoreCase("smart")){
			currentElement="smart";
			smart = new Smart();
			smart.setId(attributes.getValue("id"));
		}						
		if (elementName.equalsIgnoreCase("headphones")){
			currentElement="headphones";
			headphones = new Headphones();
			headphones.setId(attributes.getValue("id"));
		}						
		if (elementName.equalsIgnoreCase("wireless")){
			currentElement="wireless";
			wireless = new Wireless();
			wireless.setId(attributes.getValue("id"));
		}								
		if (elementName.equalsIgnoreCase("voice")){
			currentElement="voice";
			voice = new Voice();
			voice.setId(attributes.getValue("id"));
		}										
    }
	// when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {

        if (element.equals("tv")) {
			tvs.put(tv.getId(),tv);
			return;
        }
        if (element.equals("soundsystems")) {
			soundSystems.put(soundsystems.getId(),soundsystems);
			return;
        }
        if (element.equals("phone")) {
			phones.put(phone.getId(),phone);
			return;
        }
        if (element.equals("laptop")) {
			laptops.put(laptop.getId(),laptop);
			return;
        }
        if (element.equals("fitness")) {
			fitnesses.put(fitness.getId(),fitness);
			return;
        }
        if (element.equals("smart")) {
			smarts.put(smart.getId(),smart);
			return;
        }
        if (element.equals("headphones")) {
			headphoness.put(headphones.getId(),headphones);
			return;
        }
        if (element.equals("wireless")) {
			wirelesss.put(wireless.getId(),wireless);
			return;
        }
        if (element.equals("voice")) {
			voices.put(voice.getId(),voice);
			return;
        }
        if (element.equals("accessory") && currentElement.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);
			return;
        }
		if (element.equals("accessory") && currentElement.equals("tv"))
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories") && currentElement.equals("tv")) {
			tv.setAccessories(accessoryHashMap);
			accessoryHashMap=new HashMap<String,String>();
			return;
		}

        if (element.equalsIgnoreCase("image")) {
		    if(currentElement.equals("tv")){
				tv.setImage(elementValueRead);
		    }
		    if(currentElement.equals("soundsystems")){
				soundsystems.setImage(elementValueRead);
		    }
		    if(currentElement.equals("phone")){
				phone.setImage(elementValueRead);
		    }
		    if(currentElement.equals("laptop")){
				laptop.setImage(elementValueRead);
		    }
		    if(currentElement.equals("fitness")){
				fitness.setImage(elementValueRead);
		    }
		    if(currentElement.equals("smart")){
				smart.setImage(elementValueRead);
		    }
		    if(currentElement.equals("headphones")){
				headphones.setImage(elementValueRead);
		    }
		    if(currentElement.equals("wireless")){
				wireless.setImage(elementValueRead);
		    }
		    if(currentElement.equals("voice")){
				voice.setImage(elementValueRead);
		    }

		return;
        }


		if (element.equalsIgnoreCase("model")) {
            if(currentElement.equals("tv")){
				tv.setModel((elementValueRead));			
            }
            if(currentElement.equals("soundsystems")){
				soundsystems.setModel((elementValueRead));			
            }
            if(currentElement.equals("phone")){
				phone.setModel((elementValueRead));			
            }
            if(currentElement.equals("laptop")){
				laptop.setModel((elementValueRead));			
            }
            if(currentElement.equals("fitness")){
				fitness.setModel((elementValueRead));			
            }
            if(currentElement.equals("smart")){
				smart.setModel((elementValueRead));			
            }
            if(currentElement.equals("headphones")){
				headphones.setModel((elementValueRead));			
            }
            if(currentElement.equals("voice")){
				voice.setModel((elementValueRead));			
            }

            if(currentElement.equals("wireless")){
				wireless.setModel((elementValueRead));			
            }
			return;			
	    }


		if (element.equalsIgnoreCase("sku")) {
            if(currentElement.equals("tv")){
				tv.setSku(elementValueRead);			
            }
            if(currentElement.equals("soundsystems")){
				soundsystems.setSku(elementValueRead);			
            }
            if(currentElement.equals("phone")){
				phone.setSku(elementValueRead);			
            }
            if(currentElement.equals("laptop")){
				laptop.setSku(elementValueRead);			
            }
            if(currentElement.equals("fitness")){
				fitness.setSku(elementValueRead);			
            }
            if(currentElement.equals("smart")){
				smart.setSku(elementValueRead);			
            }
            if(currentElement.equals("headphones")){
				headphones.setSku(elementValueRead);			
            }
            if(currentElement.equals("wireless")){
				wireless.setSku(elementValueRead);			
            }
            if(currentElement.equals("voice")){
				voice.setSku(elementValueRead);			
            }

			return;
		}

		if (element.equalsIgnoreCase("manufacturer")) {
            if(currentElement.equals("tv")){
				tv.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("soundsystems")){
				soundsystems.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("phone")){
				phone.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("laptop")){
				laptop.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("fitness")){
				fitness.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("smart")){
				smart.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("headphones")){
				headphones.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("wireless")){
				wireless.setManufacturer(elementValueRead);			
            }
            if(currentElement.equals("voice")){
				voice.setManufacturer(elementValueRead);			
            }
			return;
		}

        if (element.equalsIgnoreCase("name")) {
            if(currentElement.equals("tv")){
				tv.setName(elementValueRead);
            }
            if(currentElement.equals("soundsystems")){
				soundsystems.setName(elementValueRead);
            }
            if(currentElement.equals("phone")){
				phone.setName(elementValueRead);
            }
            if(currentElement.equals("laptop")){
				laptop.setName(elementValueRead);
            }
            if(currentElement.equals("fitness")){
				fitness.setName(elementValueRead);
            }
            if(currentElement.equals("smart")){
				smart.setName(elementValueRead);
            }
            if(currentElement.equals("headphones")){
				headphones.setName(elementValueRead);
            }
            if(currentElement.equals("wireless")){
				wireless.setName(elementValueRead);
            }
            if(currentElement.equals("voice")){
				voice.setName(elementValueRead);
            }
			return;
	    }

        if(element.equalsIgnoreCase("price")){
			if(currentElement.equals("tv")){
				tv.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("soundsystems")){
				soundsystems.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("phone")){
				phone.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("laptop")){
				laptop.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("fitness")){
				fitness.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("smart")){
				smart.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("headphones")){
				headphones.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("wireless")){
				wireless.setPrice(Double.parseDouble(elementValueRead));			
			}
			if(currentElement.equals("voice")){
				voice.setPrice(Double.parseDouble(elementValueRead));			
			}

			return;			
        }

	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////

//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");
		Path fpath = Paths.get(TOMCAT_HOME, "webapps", "BestDealApp", "ProductCatalog.xml");
		new SaxParserDataStore(fpath.toString());
    }
}
