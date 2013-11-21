package phyml.gui.control;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Project: grisu
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 21/11/13
 * Time: 2:01 PM
 */
public class PhyMLBinary {

    private static final Logger myLogger = LoggerFactory.getLogger(PhyMLBinary.class);

    public static void main(String[] args) {

        File f = phymlBinaryFile();

        if ( f != null ) {
            System.out.println(f.getAbsolutePath());
        } else {
            System.out.println("n/a");
        }



    }

    /**
     * Method to obtain the correct path of the PhyML version to use depending
     * on the OS and OS architecture of user computer. OS arch in practice is
     * actually the bit version of the JVM.
     *
     * @return String of the absolute path of PhyML
     */
    // Method to identify the OS and bit-version of OS.
    // returns the appropriate phyML version to execute
    public static File phymlBinaryFile() {

        List<File> potentialDirs = Lists.newLinkedList();

        File binDir = null;

        // environmentVariable
        String binDirEnv = System.getProperty("PHYML_PATH");
        if (StringUtils.isNotBlank(binDirEnv)) {
            binDir = new File(binDirEnv);
            potentialDirs.add(binDir);
        }

        binDir = new File(System.getProperty("user.dir"), "bin");
        potentialDirs.add(binDir);

        File exeDir = new File(PhyMLBinary.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        binDir = exeDir;
        potentialDirs.add(binDir);

        binDir = binDir.getParentFile();
        potentialDirs.add(binDir);

        binDir = new File(binDir, "src");
        potentialDirs.add(binDir);

        binDir = new File(System.getProperty("user.dir"), "phyml");
        potentialDirs.add(binDir);

        binDir = new File(exeDir.getParentFile().getParentFile(), "bin");
        potentialDirs.add(binDir);

        binDir = new File(exeDir.getParentFile(), "bin");
        potentialDirs.add(binDir);



        return getPhymlExecutable(potentialDirs);
    } // end

    public static String getPhymlExeName() {

        String os = System.getProperty("os.name");
        String phymlVer = null;


        if (os.matches("(?i).*Windows.*")) {
            // Windows OS
            phymlVer = "PhyML-aBayes_win32.exe";
        } else if (os.matches("(?i).*Mac OS.*")) {
            // Mac OS
            phymlVer = "PhyML-aBayes_macOS_i386";
        } else if (os.matches("(i?).*Linux.*")) {
            // linux
            // see if 32 or 64 bit version
            String osArch = System.getProperty("os.arch");
            if (osArch.endsWith("64")) {
                myLogger.debug("Running 64-bit JVM, using 64-bit PhyML");
                phymlVer = "PhyML-Bayes_linux64";
            } else {
                myLogger.debug("Running 32-bit JVM, using 32-bit PhyML");
                phymlVer = "/PhyML-Bayes_linux32";
            }
        }
        if (phymlVer.equals("")) {
            throw new RuntimeException("Can't determine right version of PhyML for OS: " + os);
        }
        return phymlVer;
    }

    private static File getPhymlExecutable(List<File> potentialDirs) {
        for (File d : potentialDirs) {
            myLogger.debug("Checking directory for phyml executable: {}", d.getAbsolutePath());
            if (isPhyMLDir(d)) {
                myLogger.debug("Found phyml executable in {}: "+d.getAbsolutePath());
                return new File(d, getPhymlExeName());
            }
        }
        myLogger.debug("Could not find phyml executable.");
        return null;
    }

    private static boolean isPhyMLDir(File binDir) {
        if (binDir != null && binDir.exists() && binDir.isDirectory()) {
            File binary = new File(binDir, getPhymlExeName());
            if (binary.exists() &&
                    binary.canRead() &&
                    binary.canExecute()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
