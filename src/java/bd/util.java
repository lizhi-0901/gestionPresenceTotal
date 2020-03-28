/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import static jdk.nashorn.internal.objects.NativeArray.map;

/**
 *
 * @author lizhiwang
 */
public class util {
    public static List<?> convertObjectToList(Object obj) {
    List<?> list = new ArrayList<>();
    if (obj.getClass().isArray()) {
        list = Arrays.asList((Object[])obj);
    } else if (obj instanceof Collection) {
        list = new ArrayList<>((Collection<?>)obj);
    }
    return list;
    }
    
   public static List removeDuplicate(List list){
       for(int i=0; i<list.size()-1; i++){
           for(int j=list.size()-1;j>i;j--){
               if(list.get(j).equals(list.get(i))){
                   list.remove(j);
               }
           }
       }
       return list;
   }
   
   public static List addlist(List list, List lista){
       for(int i=0; i<lista.size();i++){
           list.add(lista.get(i));
       }
       return list;
   }
   
   public static HashMap<String, String> addMap(List liste){
       HashMap<String, String> map = new HashMap<String, String>();
                        for(int i=0;i<liste.size();i++){
                            String st=bd.output(liste,0).get(i);
                            String str=bd.output(liste,1).get(i);
                            map.put(st,str );
                        }
      return map;
   }
   
   

   
    
}



