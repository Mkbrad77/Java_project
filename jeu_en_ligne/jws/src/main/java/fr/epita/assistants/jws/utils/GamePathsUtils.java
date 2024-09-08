package fr.epita.assistants.jws.utils;

import java.io.FileNotFoundException;
import java.util.List;
    import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
public class GamePathsUtils {
    public static List<String> readMapFromPath(String path) throws IOException {
        List<String> map = new ArrayList<>();
           BufferedReader reader = null;
           try {
               reader = new BufferedReader(new FileReader(path));
           } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
           }
           String line = null;
           try {
               line = reader.readLine();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           while (line != null) {
            if (!line.startsWith("#")) {
                map.add(line.trim());
            }
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
           try {
               reader.close();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           return map;
    }
}
*/

public class GamePathsUtils {
    public static List<String> readMapFromPath(String path) throws IOException {
        // Vérifiez si le chemin est null
        if (path == null) {
            throw new IllegalArgumentException("Path provided was null");
        }

        List<String> map = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (line != null) {
            if (!line.startsWith("#")) {
                map.add(line.trim());
            }
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
