package com.yapp.lazitripper.util;

import android.util.Log;

import com.yapp.lazitripper.dto.PlaceInfoDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ohdoking on 2017. 3. 12..
 *
 *
 */

public class TravelRoute {

    List<PlaceInfoDto> routeList;

    public TravelRoute(List<PlaceInfoDto> routeList){
        this.routeList = routeList;
    }


    /*
    * item 추가
    * */
    public void addItem(PlaceInfoDto placeInfo) {
        routeList.add(placeInfo);
    }

    public List<PlaceInfoDto> getList(){
        return routeList;
    }

    /*
   * 위도 경도로 거리 계산
   *
   * @param lat1 지점 1 위도
   * @param lon1 지점 1 경도
   * @param lat2 지점 2 위도
   * @param lon2 지점 2 경도
   * @param unit 거리 표출단위
   *
   * */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return Math.floor(dist);
    }

    public ArrayList<ArrayList<Double>> getAllNodeDistance(List<PlaceInfoDto> place){
        ArrayList<ArrayList<Double>> allDistance = new ArrayList<ArrayList<Double>>();
        PlaceInfoDto temp;
        PlaceInfoDto temp2;
        ArrayList<Double> dis;

        //Log.d("distance","size:"+place.size());
        for(int i=0;i<place.size();i++){
            temp = place.get(i);
            dis = new ArrayList<Double>();

            for(int j=0;j<place.size();j++){
                temp2 = place.get(j);

                //Log.d("distance",i+","+j+":"+calculateDistance(temp.getMapx(), temp.getMapy(), temp2.getMapx(), temp2.getMapy(), "meter"));
                dis.add(Double.valueOf( calculateDistance(temp.getMapx(), temp.getMapy(), temp2.getMapx(), temp2.getMapy(), "meter")) );

            }
            allDistance.add(dis);

        }

        return allDistance;
    }

    public void showDistance(ArrayList<ArrayList<Double>> dis){
        for(int i=0;i<dis.size();i++){

            for(int j=0;j<dis.get(i).size();j++){
                //if(i == j)
                //   continue;

                Log.d("distance", "["+i+"]"+"["+j+"] : "+dis.get(i).get(j));

            }

        }

    }

    public Integer nextRoute(ArrayList<ArrayList<Double>> dis, ArrayList<Integer> node){
        //Log.d("distance",node.get(0)+"dasfsadf");

        //node.add(123);
        //Log.d("distance",node.size()+"");
        //Log.d("distance",node.get(node.size()-1)+"qwerwqr"+node.size());

        Double temp = dis.get(node.get(0)).get(0);
        Double temp2 = dis.get(node.get(node.size()-1)).get(0);
        //Integer a = new Integer(-1);
        //Integer b = new Integer(-1);
        int a=99,b=99;

        for(int i=0;i<dis.get(node.get(0)).size();i++){
            if(!node.contains(Integer.valueOf(i))){
                temp = dis.get(node.get(0)).get(i);
                a = i;
                break;
            }
        }
        for(int i=0;i<dis.get(node.get(node.size()-1)).size();i++){
            if(!node.contains(Integer.valueOf(i))){
                temp2 = dis.get(node.get(node.size()-1)).get(i);
                b = i;
                break;
            }
        }

        for(int i=0;i<dis.get(node.get(0)).size();i++){
            if(node.contains(Integer.valueOf(i))){
                //Log.d("distance","i값 : "+i);
                continue;
            }
            //a = i;
            if(temp > dis.get(node.get(0)).get(i)) {
                Log.d("distance", "if안" + i);
                temp = dis.get(node.get(0)).get(i);
                a = i;
            }

        }

        for(int i=0;i<dis.get(node.get(node.size()-1)).size();i++){
            if(node.contains(Integer.valueOf(i))){
                continue;

            }

            //b = i;
            if(temp2 > dis.get(node.get(node.size()-1)).get(i)){
                temp2 = dis.get(node.get(node.size()-1)).get(i);
                b = i;
            }


        }

        Log.d("distance","temp:"+temp+"temp2:"+temp2);
        if(temp < temp2){
            Log.d("distance", a+"aa");
            node.add(0,new Integer(a));
            return a;
        }else {
            Log.d("distance",b+"bb");
            node.add(node.size(),new Integer(b));
            return b;
        }


    }


    public ArrayList<Integer> findShortRoute(ArrayList<ArrayList<Double>> dis){
        ArrayList<Integer> connectedNode = new ArrayList<Integer>();

        Double shortDis = dis.get(0).get(1);

        for(int i=0;i<dis.size();i++){

            for(int j=0;j<dis.get(i).size();j++){
                if(i == j)
                    continue;

                if(shortDis > dis.get(i).get(j)){
                    shortDis = dis.get(i).get(j);
                    connectedNode.clear();
                    connectedNode.add(new Integer(i));
                    connectedNode.add(new Integer(j));
                    Log.d("distance",connectedNode.get(0)+" aaaa"+connectedNode.get(1));

                }

            }

        }



        for(int i=0;i<dis.size()-2;i++){
            nextRoute(dis,connectedNode);
        }

        ArrayList<Integer> route = new ArrayList<Integer>();
        for(int i=0;i<dis.size();i++){
            //Log.d("distance","순서대로 : "+connectedNode.get(i));
            route.add(connectedNode.get(i));
        }

        //Log.d("distance",connectedNode.get(0)+" "+connectedNode.get(1)+" "+connectedNode.get(2)+" "+connectedNode.get(3)+" "+connectedNode.get(4)+" ");
        return route;
    }

    public ArrayList<PlaceInfoDto> changeIndex(List<PlaceInfoDto> dto,ArrayList<Integer> index){

        if(dto.size() != index.size()) {
            Log.d("distance","이건 문제가 있다.");
            return null;
        }

        ArrayList<PlaceInfoDto> temp = new ArrayList<PlaceInfoDto>();
        for(int i=0;i<dto.size();i++){
            temp.add(dto.get(index.get(i)));
            Log.d("distance",index.get(i)+" hihi");
        }

        return temp;
    }


    /*
    * 최단 경로의 랜드마크를 찾는다.
    * */
    public ArrayList<PlaceInfoDto> findShortRoute(){
        ArrayList<ArrayList<Double>> allDistance;
        allDistance = getAllNodeDistance(routeList);
        showDistance(allDistance);


        return changeIndex(routeList,findShortRoute(allDistance));


        //플로이드로...
        /*ArrayList<ArrayList<Double>> allDistance;
        allDistance = getAllNodeDistance(routeList);
        showDistance(allDistance);
        Log.d("floyd","플로이드 쓰고난후 최단거리들");
        ArrayList<ArrayList<Double>> temp = findShortRoute(allDistance);

        showDistance(temp);

        return null;*/
    }

    //////////////////////////////플로이드알고리즘
    /*public ArrayList<ArrayList<Double>> findShortRoute(ArrayList<ArrayList<Double>> dis){

        ArrayList<ArrayList<Double>> shortDis = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> temp;


        int size = dis.size();

        for(int k=0;k<size;k++){

            for(int i=0;i<size;i++){
                temp = new ArrayList<Double>();

                for(int j=0;j<size;j++){

                    temp.add( Math.min(dis.get(i).get(j), dis.get(i).get(k) + dis.get(k).get(j)) );

                    //shortDis.get(i).set(j, Math.min(dis.get(i).get(j), dis.get(i).get(k) + dis.get(k).get(j) ));

                }

                shortDis.add(temp);
            }


        }


        return shortDis;

    }*/




    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
