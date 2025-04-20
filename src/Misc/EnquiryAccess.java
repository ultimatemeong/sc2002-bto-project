package Misc;

import java.util.HashMap;
import java.util.Map;

import Enquiries.Enquiry;
import Users.User;

public class EnquiryAccess implements AccessControl<Enquiry> {
    private static Map<String, Map<Integer, String>> enquiryAccessMap = new HashMap<>();

    public String check(Enquiry enquiry, User user) {
        Integer id = enquiry.getId();
        String userNRIC = user.getNric();
        Map<Integer, String> userAccessMap;
        
        if (!enquiryAccessMap.containsKey(userNRIC)) {
            enquiryAccessMap.put(userNRIC, new HashMap<>());
            userAccessMap = enquiryAccessMap.get(userNRIC);
        } else {
            userAccessMap = enquiryAccessMap.get(userNRIC);
        }

        if (!userAccessMap.containsKey(id)) {
            if (user.getClass().getSimpleName().equals("Manager")) {
                return "R"; // Manager has read access to all enquiries
            } else if (user.getClass().getSimpleName().equals("Officer") && enquiry.getProject().getOfficerList().contains(user)) {
                return "R"; // Officer has read access to all enquiries for projects he is involved in
            } else {
                return "NULL"; // If user is applicant and not in accessmap
            }
        } else {
            return userAccessMap.get(id); // Return the access level if it exists
        }
    }

    public void add(Enquiry enquiry, User user, String accessLevel){
        Integer id = enquiry.getId();
        String userNRIC = user.getNric();
        enquiryAccessMap.putIfAbsent(userNRIC, new HashMap<>());
        enquiryAccessMap.get(userNRIC).put(id, accessLevel);
    }

    public void delete(Enquiry enquiry, User user) {
        Integer id = enquiry.getId();
        String userNRIC = user.getNric();
        if (enquiryAccessMap.containsKey(userNRIC)) {
            enquiryAccessMap.get(userNRIC).remove(id);
        }
    }
}
