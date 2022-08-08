package talent.ql.user_age.service;

/**
 * @author Brume
 **/
public interface AgeService {
    /**
     * This method takes in the timestamp of the user in the  2007-12-03T10:15:3 yyyy- format
     *
     * @param timeStamp
     * @return long
     */
    long calculateAge(String timeStamp);
}
