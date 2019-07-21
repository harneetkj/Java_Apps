package ca.jrvs.apps.Grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepImpl implements JavaGrep {
    private String rootPath;
    private String regex;
    private String Outfile;

    @Override
    public void process() throws IOException {
        String path = this.getRootPath();
        List<String> lines = new ArrayList<String>();
        List<File> files = new ArrayList<File>();
        files = listFiles(path);
        ArrayList<String> matched = new ArrayList<>();
        for (File file : files) {
            lines = readLines(file);
            for (String line : lines) {
                if (containsPattern(line)) {
                    matched.add(line);
                }
            }
        }

        writeToFile(matched);


    }

    @Override
    public List<File> listFiles(String rootDir) {
        File folder = new File(rootDir);
        File[] listOfFiles = folder.listFiles() ;

        List<File> results = new ArrayList<>();

        if(listOfFiles==null){
            results.add(folder);
            return  results;
        }

        for(File file: listOfFiles)
        {
            if(file.isFile()){
                results.add(file);

            }
            else if (file.isDirectory()){
                results.addAll(listFiles(file.getAbsolutePath()));

            }

        }

        return results;
    }


    @Override
    public List<String> readLines(File inputFile){
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            String st;
            while ((st = br.readLine()) != null) {
                lines.add(st);
            };
        }
        catch (IOException ie)
        {
            ie.printStackTrace();
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {

        return line.matches(this.getRegex());


    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter br1 = new BufferedWriter(new FileWriter(this.getOutFile()));
        for (String line: lines)
        {
            br1.write(line);
        }
        br1.close();
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath=rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex=regex;
    }

    @Override
    public String getOutFile() {
        return  this.Outfile;
    }

    @Override
    public String setOutFile(String Outfile) {
        return this.Outfile=Outfile;
    }

    public static void main(String[] args) {

        if(args.length !=3){
            throw new IllegalArgumentException("USAGE :regex rootpath outfile");
        }
        JavaGrepImpl javaGrepImp = new JavaGrepImpl();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);
        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
