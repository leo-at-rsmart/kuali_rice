/**
 * 
 */
package edu.samplu.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import sun.applet.Main;

/**
 * @author dmoteria
 * 
 */
public class FreemarkerTmplMthdSmoketestGenerator {
    private static Configuration cfg = new Configuration();

    // Templates for File Generation
    private static String DIR_TMPL = "/Gen/";
    
    //Configuration
    private static TemplateLoader templateLoader = new ClassTemplateLoader(Main.class, DIR_TMPL);

    public static void main(String[] args) throws Exception {
        cfg.setTemplateLoader(templateLoader);
        
        String DEFAULT_PROPS_LOCATION = "/GenFiles/MainTmplMthdSTNavBase.properties";
        String TMPLMTHDSTNAVBASE_TMPL = "TmplMthdSTNavBase.ftl";
              
        //Here we can prepare a list of template & properties file and can iterate to generate files dynamically on single run.
        createFile(DEFAULT_PROPS_LOCATION,TMPLMTHDSTNAVBASE_TMPL);
    }

    private static void createFile(String DEFAULT_PROPS_LOCATION, String TMPL) throws Exception
    {
        try {
            //Loading Properties file
            Properties props = new Properties();
            InputStream in = null;
            in = Main.class.getResourceAsStream(DEFAULT_PROPS_LOCATION);

            if (in != null) {
                props.load(in);
                in.close();
            }
            else
            {
                throw new Exception("Problem with input stream.");
            }

            // build file STJUnitBase and add to array              
            File f1 = new File("src" + File.separatorChar + "it" + File.separatorChar + "resources"
                    + File.separatorChar + "GenFiles" + File.separatorChar + props.getProperty("className")
                    + TMPL.substring(0, TMPL.length() - 4) + ".java");

            String output1 = FreeMarkerTemplateUtils.processTemplateIntoString(cfg.getTemplate(TMPL),
                    props);
            FileUtils.writeStringToFile(f1, output1);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to generate files", e);
        }

    }

}