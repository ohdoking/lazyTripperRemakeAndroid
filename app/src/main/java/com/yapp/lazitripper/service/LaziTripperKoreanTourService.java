package com.yapp.lazitripper.service;

import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.dto.RegionCode;
import com.yapp.lazitripper.dto.RegionResultDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ohdok on 2017-02-24.
 */

public interface LaziTripperKoreanTourService {

    /*
        지역 코드 정보

        numOfRows	한 페이지 결과 수	10		한 페이지 결과 수
        pageNo	페이지 번호	1		현재 페이지 번호
        MobileOS	OS 구분	ETC	필수	IOS (아이폰), AND (안드로이드),
        WIN (윈도우폰), ETC
        MobileApp	서비스명		필수	서비스명=어플명
        areaCode	지역코드			지역코드, 시군구코드
    */
    @GET("rest/KorService/areaCode")
    Call<CommonResponse<RegionResultDto>> getRelionInfo(
            @Query("numOfRows") Integer numOfRows,
            @Query("pageNo") Integer pageNo,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp);

    /*

        서비스 분류 코드 조회

        numOfRows	한 페이지 결과 수	10		한 페이지 결과 수
        pageNo	페이지 번호	1		현재 페이지 번호
        MobileOS	OS 구분	ETC	필수
            IOS (아이폰), AND (안드로이드),
        WIN (윈도우폰), ETC
        MobileApp	서비스명		필수	서비스명=어플명
        contentTypeId	관광타입 ID			관광타입(관광지, 숙박 등) ID
        cat1	대분류			대분류 코드
        cat2	중분류		cat1	중분류 코드
        cat3	소분류		cat1, cat2	소분류 코드

    */

    @GET("rest/KorService/areaCode")
    Call<RegionCode> getServiceInfo(
            @Path("numOfRows") String numOfRows,
            @Path("pageNo") String pageNo,
            @Path("MobileOS") String MobileOS,
            @Path("MobileApp") String MobileApp,
            @Path("areaCode") String areaCode,
            @Path("cat1") String cat1,
            @Path("cat2") String cat2,
            @Path("cat3") String cat3);

    /*
        도시 기준으로 장소 정보 제공

        numOfRows	한 페이지 결과 수	10		한 페이지 결과 수
        pageNo	페이지 번호	1		현재 페이지 번호
        arrange	정렬 구분	A		(A=제목순, B=조회순, C=수정일순, D=생성일순)
        대표이미지 정렬 추가 (O=제목순, P=조회순, Q=수정일순, R=생성일순)
        listYN	목록 구분	Y		목록 구분 (Y=목록, N=개수)
        MobileOS	OS 구분	ETC	필수
            IOS (아이폰), AND (안드로이드),
        WIN (윈도우폰), ETC
        MobileApp	서비스명		필수	서비스명=어플명
        contentTypeId	관광타입 ID			관광타입(관광지, 숙박 등) ID
        areaCode	지역코드			지역코드
        sigunguCode	시군구코드		areaCode	시군구코드
        cat1	대분류			대분류 코드
        cat2	중분류		cat1	중분류 코드
        cat3	소분류		cat1, cat2	소분류 코드
    */
    @GET("rest/KorService/areaBasedList")
    Call<RegionCode> getPlaceInfoByCity(
            @Path("numOfRows") String numOfRows,
            @Path("pageNo") String pageNo,
            @Path("arrange") String arrange,
            @Path("listYN") String listYN,
            @Path("MobileOS") String MobileOS,
            @Path("MobileApp") String MobileApp,
            @Path("contentTypeId") String contentTypeId,
            @Path("areaCode") String areaCode,
            @Path("sigunguCode") String sigunguCode,
            @Path("cat1") String cat1,
            @Path("cat2") String cat2,
            @Path("cat3") String cat3);

    /*
        위치 기준으로 장소 정보 제공

        numOfRows	한 페이지 결과 수	10		한 페이지 결과 수
        pageNo	페이지 번호	1		현재 페이지 번호
        arrange	정렬 구분	A		(A=제목순, B=조회순, C=수정일순, D=생성일순, E=거리순)
        대표이미지 정렬 추가 (O=제목순, P=조회순, Q=수정일순, R=생성일순, S=거리순)
        listYN	목록 구분	Y		목록 구분 (Y=목록, N=개수)
        MobileOS	OS 구분	ETC	필수	IOS (아이폰), AND (안드로이드),
        WIN (윈도우폰), ETC
        MobileApp	서비스명		필수	서비스명=어플명
        contentTypeId	관광타입 ID			관광타입(관광지, 숙박 등) ID
        mapX	X좌표		필수	GPS X좌표(WGS84 경도 좌표)
        mapY	Y좌표		필수	GPS Y좌표(WGS84 위도 좌표)
        radius	거리 반경		필수	거리 반경(단위:m) , Max값 20000m=20Km
    */
    @GET("rest/KorService/locationBasedList")
    Call<RegionCode> getPlaceInfoByLocation(
            @Path("numOfRows") String numOfRows,
            @Path("pageNo") String pageNo,
            @Path("arrange") String arrange,
            @Path("listYN") String listYN,
            @Path("MobileOS") String MobileOS,
            @Path("MobileApp") String MobileApp,
            @Path("contentTypeId") String contentTypeId,
            @Path("mapX") String mapX,
            @Path("mapY") String mapY,
            @Path("radius") String radius);

}
