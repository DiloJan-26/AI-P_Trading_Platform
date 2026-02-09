package com.eztrad.servercomp.controller;

import com.eztrad.servercomp.config.JwtProvider;
import com.eztrad.servercomp.model.TwoFactorOTP;
import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.UserRepository;
import com.eztrad.servercomp.response.AuthResponse;
import com.eztrad.servercomp.service.CustomUserDetailsService;
import com.eztrad.servercomp.service.EmailService;
import com.eztrad.servercomp.service.TwoFactorOTPService;
import com.eztrad.servercomp.service.WatchlistService;
import com.eztrad.servercomp.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

// step 11 - auth controller created , set and get user credentials
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // belongs to step 29 for login
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // belongs to step 37
    @Autowired
    private TwoFactorOTPService twoFactorOTPService;

    // belongs to step 39
    @Autowired
    private EmailService emailService;

    // belongs to testing period above 115 step - 119
    private WatchlistService watchlistService;


    @PostMapping ("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {


        // Step 24 - email existence checkup
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if(isEmailExist!=null){
            throw new Exception("email is already used with another account");
        }

        // Step 11 - this and end return part is_belongs to step 11 not step 24 or 26
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);

        // belongs to testing period above 115 step - 119
//        watchlistService.createWatchlist(savedUser);
        // above only belongs to step -119

        // this also belongs to Step 24 - copied from JwtTokenValidator and modified the getters
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        //step 26 - create jwt token
        String jwt = JwtProvider.generateToken(auth);

        //step 28 - from now our signup method is perfectly ready
        // when you go and post at http://localhost:5455/auth/signup in postman your credential return a jwt token
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("register success");


        // after the 28th step res parameter added for return but it belongs to step 11
        return new ResponseEntity<>(res, HttpStatus.CREATED);

        // step 12 - go with postman check the post method with the url of http://localhost:5455/auth/signup to create user
        // step 13 - next add the jwt dependencies to pom.yml -> from mvn repository(jjwt-api, jjwt-impl, jjwt-jackson)
        // step 14 - at first(next to step-1) you made the spring security dependency be commented now you can un comment it
        // step 15 - so without spring security the http://localhost:5455 will showup the message that you wrote
        //           at HomeController but with it you can see the signup/login page and in terminal there is a line
        //           with generated password (user : user , password : prompted in terminal like
        //           Using generated security password: adbc4e24-43c6-46c9-904d-0b6f6fdbcf8b  )




    }
    //-------------------------------------------------------
    // step 29 - lets create login method
    // it entirely copied like above post_mapping of signup and modified for login
    @PostMapping ("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(userName); // belongs to step 37

        // step 35 - 2 factor auth enabled what to do
        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("TwoFactorAuth is Enabled");
            res.setIsTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            // Step 37 - after otp_utils creation
            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUser(authUser.getId());
            if(oldTwoFactorOTP!=null){
                twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOtp(
                    authUser,
                    otp,
                    jwt
            );

            // Step 39 - mail_sender
            emailService.sendVerificationOtpEmail(userName,otp);
            // above part only related to step 39

            res.setSession(newTwoFactorOTP.getId());
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        // down part is not belongs to step 35 that belongs step 29

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("login success");

        return new ResponseEntity<>(res, HttpStatus.CREATED); // this is belongs to step 37
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }

        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return  new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    // Step 40 - varifying login otp
    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySighInOtp(
            @PathVariable String otp,
            @RequestParam String id) throws Exception {

        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);

        if(twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTP,otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("two factor authentication verified");
            res.setIsTwoFactorAuthEnabled(true);
            res.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("invalid otp");
    }
}
