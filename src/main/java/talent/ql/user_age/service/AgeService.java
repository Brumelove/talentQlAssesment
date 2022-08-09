package talent.ql.user_age.service;

/**
 * @author Brume
 **/
public interface AgeService {
    /**
     * This method takes in the timestamp of the user in the  2007-12-03 10:15:03 format
     *
     * @param dob
     * @return long
     */
    long calculateAge(String dob);
}
