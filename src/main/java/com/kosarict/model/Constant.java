package com.kosarict.model;

/**
 * Created by Sadegh-Pc on 11/30/2016.
 */
public class Constant {

    public static String CHECK_USER_URL = "http://localhost:8083/ws/api/checkUser?";

    public static int Complaint = 3;
    public static int Appereciation = 1;
    public static int Criticize = 4;
    public static int Offer = 2;

    public static short AppreciationTicketTypeId = 1;
    public static short ComplainTicketTypeId = 3;
    public static short OfferTicketTypeId = 2;
    public static short CriticismTicketTypeId = 4;

    public static short SendTypeSite = 1;

    public static short finishTicketStatus = 3;

    public static short Pending = 1;
    public static short Errand = 2;
    public static short Ended = 3;
    public static short IsRead = 4;

    public static int NotificationTabId = 6;

    public static int PendingStatus = 1;
    public static int EnableStatus = 2;
    public static int DisableStatus = 3;

    public static short AdminSection = 1;
    public static short ManagerRole=1;
    public static short MiddelManagerRole=2;

    public static String userImageUrl = "http://192.168.1.5:8071/static/userImage/";
    public static String attachmentUrl = "http://192.168.1.5:8071/static/attachment/";

    public static String INCORRECT = ".%D9%86%D8%A7%D9%85%20%DA%A9%D8%A7%D8%B1%D8%A8%D8%B1%DB%8C%20%D9%88%20%DB%8C%D8%A7%20%DA%A9%D9%84%D9%85%D9%87%20%D8%B9%D8%A8%D9%88%D8%B1%20%D9%86%D8%A7%D8%AF%D8%B1%D8%B3%D8%AA%20%D8%A7%D8%B3%D8%AA";
    public static String LOGOUT = ".%D8%AE%D8%B1%D9%88%D8%AC%20%D8%A8%D8%A7%20%D9%85%D9%88%D9%81%D9%82%DB%8C%D8%AA%20%D8%A7%D9%86%D8%AC%D8%A7%D9%85%20%D8%B4%D8%AF";
}
