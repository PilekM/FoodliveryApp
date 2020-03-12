package com.example.foodliveryapp.database;

public class Services {

    private static String SERVER_URL = "http://10.0.2.2:3000";
    public static final  String LOGIN = SERVER_URL + "/user_login/";
    public static final String REGISTER = SERVER_URL + "/register/";
    public static final String GET_ORDERS = SERVER_URL + "/get_all_orders_for_user/";
    public static final String GET_WORKERS = SERVER_URL + "/get_all_workers/";
    public static final String GET_RESTAURANTS = SERVER_URL + "/get_all_restaurants/";
    public static final String CHECK_CODE = SERVER_URL + "/check_reg_code/";
    public static final String DELETE_OLD_TIMESTAMPS = SERVER_URL + "/delete_timestamps/";
    public static final String GET_DETAILED_ORDER = SERVER_URL + "/get_detail_order/";
    public static final String DENY_ORDER = SERVER_URL + "/deny_order/";
    public static final String UPDATE_LOCATION = SERVER_URL + "/update_location/";
    public static final String ACCEPT_ORDER = SERVER_URL + "/accept_order/"; // ustaw status zaakceptowane
    public static final String WAIT_REST_ORDER = SERVER_URL + "/wait_res_order/"; //ustaw status wait in restaurant
    public static final String DELIVER_ORDER = SERVER_URL + "/deliver_order/"; // ustaw status delivering order
    public static final String DRIVE_COMPLETED_ORDER = SERVER_URL + "/drive_completed_order/"; // ustaw status finalizing order
    public static final String DELIVERED_ORDER = SERVER_URL + "/delivered_order/"; // ustaw status delivered
    public static final String NOT_DELIVERED_ORDER = SERVER_URL + "/not_delivered_order"; //ustaw status not delivered
    public static final String COMPLETED_ORDER = SERVER_URL + "/completed_order/"; // zakoncz
    public static final String UPDATE_AVAILABILITY_FORM = SERVER_URL + "/update_availability_form/";
    public static final String GET_AVAILABILITY_FORM = SERVER_URL + "/get_availability_form/";
    public static final String GET_OPINION_FORM = SERVER_URL + "/get_opinion_form/";
    public static final String UPDATE_OPINION_FORM = SERVER_URL + "/update_opinion_form/";
    public static final String UPDATE_PASSWORD = SERVER_URL + "/update_password/";
    public static final String UPDATE_PHONE = SERVER_URL + "/update_phone/";
    public static final String GET_DAILY_STATS = SERVER_URL + "/get_daily_stats/";
    public static final String GET_DAILY_TOP_REST = SERVER_URL + "/get_daily_top_rest/";
    public static final String GET_MONTHLY_STATS = SERVER_URL + "/get_monthly_stats";
    public static final String GET_MONTHLY_TOP_REST = SERVER_URL + "/get_monthly_top_rest/";
}
