package Ui;

import java.io.*;

public class Config {
    private int currentLanguageIndex;
    private int currentResolutionIndex;
    private int currentDisplayIndex;
    public int GetLanguageIndex(){
        return currentLanguageIndex;
    }

    public int GetResolutionIndex(){
        return currentResolutionIndex;
    }

    public int GetDisplayIndex(){
        return currentDisplayIndex;
    }

    public Config(){
    }

    public void saveConFig(int currentLanguageIndex,int currentResolutionIndex,int currentDisplayIndex){
        this.currentLanguageIndex = currentLanguageIndex;
        this.currentResolutionIndex = currentResolutionIndex;
        this.currentDisplayIndex = currentDisplayIndex;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //Resolution
            bw.write(String.valueOf(currentResolutionIndex));
            bw.newLine();

            //Language
            bw.write(String.valueOf(currentLanguageIndex));
            bw.newLine();

            //Display Mode
            bw.write(String.valueOf(currentDisplayIndex));
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            //Resolution
            String s = br.readLine();
            currentResolutionIndex = Integer.parseInt(s);
            //Language
            s = br.readLine();
            currentLanguageIndex = Integer.parseInt(s);

            //Display Mode
            s=br.readLine();
            currentDisplayIndex = Integer.parseInt(s);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
