package Test;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public class Writer {
    private String path;
    private List<Point> list = new LinkedList<Point>();

    public Writer (String path, List<Point> list){
        this.path=path;
        this.list=list;
    }

    public void write() throws Exception{
        File file = new File(path);
        if (! file.exists()) {
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("New file was created.");
            } else {
                System.out.println("Unexpected error occured.");
            }
        }
        try {
            FileWriter fw = new FileWriter(file, true);
            if (list!=null&&list.size()>0){
                fw.append("POLYGON ((");
                for (int i=0;i<list.size(); i++) {
                    if (i!=list.size()-1)fw.append(""+list.get(i).getX()+" "+ list.get(i).getY()+", ");
                    if (i==list.size()-1)fw.append(""+list.get(i).getX()+" "+ list.get(i).getY()+"))");
                }
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
