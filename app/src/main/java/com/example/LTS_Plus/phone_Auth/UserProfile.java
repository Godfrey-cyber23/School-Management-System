package com.example.LTS_Plus.phone_Auth;

/**
 * Represents a user's profile with a username and unique user ID (UID).
 * Used for:
 * - Storing user data in Firebase or other databases.
 * - Transferring user data between activities/fragments.
 * - Updating or displaying user profile information.
 * - Communicating user data with APIs.
 */
public class UserProfile {

    // Stores the username of the user.
    public final String username;

    // Stores the unique ID of the user (used for identification in databases like Firebase).
    public final String userUID;

    /**
     * Parameterized constructor.
     * Used when creating a new user profile with a username and UID.
     * Example: When registering a new user or initializing a profile object from database data.
     *
     * @param username The name of the user.
     * @param userUID  The unique identifier for the user.
     */
    public UserProfile(String username, String userUID) {
        this.username = username;
        this.userUID = userUID;
    }

    /**
     * Gets the username.
     * Used in:
     * - Displaying the username in UI components (e.g., profile screen, leaderboards).
     * - Sending the username to APIs or other services.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }
}
