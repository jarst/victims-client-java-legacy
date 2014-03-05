

package com.redhat.victims.cli;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gm
 */
public class CommandLineOptionsTest {
    
    public CommandLineOptionsTest() {
    }

    @Test
    public void testAddOption() {
    }

    @Test
    public void testParse() {
        
//            CommandLineOption(String name, 
//                String description, 
//            boolean required, 
//            boolean expectsArgument,
//            boolean isFlag, 
//            T defaultValue){
    
        CommandLineOptions opts = new CommandLineOptions("victims-client");
        opts.addOption(
            new CommandLineOption<Boolean>("-debug", "show debug output", false, 0, true, null, Boolean.class));
        opts.addOption(
            new CommandLineOption<Boolean>("-help", "show this help message", false, 0, true, null, Boolean.class));
        opts.addOption(
            new CommandLineOption<Integer>("-count", "example integer", false, 1, false, null, Integer.class));
        opts.addOption(
            new CommandLineOption<String>("-key", "example kv entry", false, 1, false, null, String.class));
        opts.addOption(
                new CommandLineOption<String>("-multi", "example multiple arguments", false, 2, false, null, String.class));
        
        // Empty arguments
        String[] emptyArguments = {};
        assert(opts.parse(emptyArguments));
        
        String[] listOfFiles = { "file1.txt", "file2.txt", "file3.txt" };
        if (! opts.parse(listOfFiles)){
            System.out.println(opts.getUsage());
        } else {
            System.out.println("You entered the following arguments: "); 
        }
        
        // Test flags
        String[] helpArgument = {"-help"};
        assertTrue(opts.parse(helpArgument));
        CommandLineOption opt = opts.getOptions().get("-help");
        assertTrue(opts.getOptions().get("-help").hasValue());
        opts.reset();
        assertTrue(! opts.getOptions().get("-help").hasValue());
        
        // Test invalid arguments
        String[] invalidArgument  = { "-count", "foo" };
        assertTrue(! opts.parse(invalidArgument));
        opts.reset();
        
        // Test key -> value entry
        String[] keyValueArgument = { "-key=value" };
        assertTrue(opts.parse(keyValueArgument));
        assertTrue(opts.getOptions().get("-key").getValue().equals("value"));
        
        // Test multiple arguments 
        String[] multipleArguments = {"-multi", "foo", "bar" };
        String[] multipleButNotEnough = { "-multi", "foo" };
        
        assertTrue(opts.parse(multipleArguments));
        opts.reset();
        assertTrue(!opts.parse(multipleButNotEnough));
        
        // Test missing required 
        opts.addOption(
                new CommandLineOption<String>("-foo", "example foo", true, 1, false, null, String.class ));
        assertTrue(! opts.parse(emptyArguments));
        
        String[] requiredArgument = { "-foo", "bar" };
        opts.reset();
        
        assertTrue(opts.parse(requiredArgument));
        
       
        
    }
    
//    @Test
//    public void testScanDir(){
//        
//        Main prog = new Main();
//        prog.runWithArgs(new String[] {"--verbose",  ".testdata" });
//        
//    }
//    
//    @Test
//    public void testDumpJar(){
//        Main prog = new Main();
//        prog.runWithArgs(new String[]{ "--jar-info", ".testdata/org/springframework/spring/2.5.6/spring-2.5.6.jar"});
//    }


}
