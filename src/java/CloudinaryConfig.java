/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author herme
 */

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {
        public static Cloudinary cloudinary;

    static {
        try {
            // Adding 'secure', true is a best practice for modern deployments
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqgbwotpy",
                "api_key", "887129975798763",
                "api_secret", "qjDRdzJ-kgT7y1hBTUg5W_nPcYM",
                "secure", true 
            ));
            System.out.println("Cloudinary initialized successfully.");
        } catch (Exception e) {
            // This will help you see the REAL error in Render logs
            System.err.println("FATAL: Cloudinary failed to initialize: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
