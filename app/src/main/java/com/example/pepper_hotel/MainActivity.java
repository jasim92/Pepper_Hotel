package com.example.pepper_hotel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.LocalizeAndMapBuilder;
import com.aldebaran.qi.sdk.builder.LocalizeBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayPosition;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.actuation.ExplorationMap;
import com.aldebaran.qi.sdk.object.actuation.Localize;
import com.aldebaran.qi.sdk.object.actuation.LocalizeAndMap;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionImportance;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionValidity;
import com.aldebaran.qi.sdk.object.conversation.Bookmark;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.EditablePhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Phrase;
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.QiChatVariable;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.human.Human;
import com.aldebaran.qi.sdk.object.humanawareness.EngageHuman;
import com.aldebaran.qi.sdk.object.humanawareness.HumanAwareness;
import com.example.pepper_hotel.Fragments.CityFragment;
import com.example.pepper_hotel.Fragments.CleaningFragment;
import com.example.pepper_hotel.Fragments.HomeFragment;
import com.example.pepper_hotel.Fragments.HotelDetailFragment;
import com.example.pepper_hotel.Fragments.HotelFragment;
import com.example.pepper_hotel.Fragments.LaundryFragment;
import com.example.pepper_hotel.Fragments.PlaceDetailFragment;
import com.example.pepper_hotel.Fragments.PlaceFragment;
import com.example.pepper_hotel.Fragments.RestaurantFragment;
import com.example.pepper_hotel.Fragments.RoomFragment;
import com.example.pepper_hotel.Fragments.SplashScreenFragment;
import com.example.pepper_hotel.Fragments.TaxiConfirmFragment;
import com.example.pepper_hotel.Fragments.TaxiFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    Topic topic;
    QiChatbot qiChatbot;
    Chat chat;

    public FragmentManager fragmentManager;
    public HomeFragment homeFragment;
    public CityFragment cityFragment;
    public TaxiFragment taxiFragment;
    public TaxiConfirmFragment taxiConfirmFragment;
    public HotelFragment hotelFragment;
    public RestaurantFragment restaurantFragment;
    public PlaceDetailFragment placeDetailFragment;
    public PlaceFragment placeFragment;
    public HotelDetailFragment hotelDetailFragment;
    public RoomFragment roomFragment;
    public LaundryFragment laundryFragment;
    public CleaningFragment cleaningFragment;
    public SplashScreenFragment splashScreenFragment;
    public PlaceDTO placeDTOtmp;

    private HumanAwareness humanAwareness;
    private Human recommendedHuman, nextHumen;
    QiContext qiContext;
    String state = "STATE_SOLITARY";
    private LocalizeAndMap localizeAndMap;
    // Store the LocalizeAndMap execution.
    private Future<Void> localizationAndMapping;
    // Store the map.
    private ExplorationMap explorationMap;
    // Store the Localize action.
    private Localize localize;

    private EditablePhraseSet cityList;
    private EditablePhraseSet placeList;

    private List<String> qiVariables = new ArrayList<>(Arrays.asList("place_description", "hotel_description"));
    public Map<String, Map<String, Bookmark>> bookmarks;
    public Map<String, QiChatVariable> variables;
    private Future<Void> currentGotoBookmarkFuture;

    CountDownNoInteraction countDownNoInteraction;
    Button back_btn;
    ImageButton home_btn;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QiSDK.register(this,this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);
        setSpeechBarDisplayPosition(SpeechBarDisplayPosition.TOP);

        countDownNoInteraction = new CountDownNoInteraction(this, 300000, 10000);
        countDownNoInteraction.start();

        ip = getIntent().getStringExtra("ip");

        homeFragment = HomeFragment.newInstance("",ip);
        cityFragment = CityFragment.newInstance("",ip);
        taxiFragment = TaxiFragment.newInstance("",ip);
        taxiConfirmFragment = TaxiConfirmFragment.newInstance("",ip);
        hotelFragment = HotelFragment.newInstance("",ip);
        restaurantFragment = RestaurantFragment.newInstance("",ip);
        placeDetailFragment = PlaceDetailFragment.newInstance("",ip);
        placeFragment = PlaceFragment.newInstance("",ip);
        hotelDetailFragment = HotelDetailFragment.newInstance("",ip);
        roomFragment = RoomFragment.newInstance("",ip);
        splashScreenFragment = SplashScreenFragment.newInstance("",ip);
        laundryFragment = LaundryFragment.newInstance("",ip);
        cleaningFragment = CleaningFragment.newInstance("",ip);

        FragmentTransaction fragmentTransaction;
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, homeFragment);
        fragmentTransaction.commit();

    }

    public void showFragment(Fragment[] fragments, Fragment newFragment)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (newFragment.isAdded())
            fragmentTransaction.show(newFragment);
        else
            fragmentTransaction.add(R.id.fragment_layout,newFragment,"A").addToBackStack("root_fragment");
        for (int i=0; i<fragments.length; i++)
        {
            if (fragments[i].isAdded())
                fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        this.qiContext = qiContext;

         topic = TopicBuilder.with(qiContext).withResource(R.raw.chat).build();
         qiChatbot = QiChatbotBuilder.with(qiContext).withTopic(topic).build();

        Map<String, QiChatExecutor> executors = new HashMap<>();
        executors.put("StatusExecutor", new StatusExecutor(qiContext, this, new MediaPlayer(), getApplicationContext()));

        bookmarks = new HashMap<>();
        for (Topic t : qiChatbot.getTopics()) {
            bookmarks.put(t.getName(), t.getBookmarks());
        }

        variables = new HashMap<>();
        for (String qiVariableName : qiVariables) {
            variables.put(qiVariableName, qiChatbot.variable(qiVariableName));
        }

        cityList = qiChatbot.dynamicConcept("city_list");
        placeList = qiChatbot.dynamicConcept("place_list");

        addCity(cityFragment.cityString);

        addPlace(placeFragment.placeString);

        qiChatbot.setExecutors(executors);

        chat = ChatBuilder.with(qiContext).withChatbot(qiChatbot).build();
        chat.async().run();
        chat.async().addOnNormalReplyFoundForListener(input -> {
            countDownNoInteraction.reset();
        });
        startMapping(qiContext);
//        startHumanAwareness();


    }


    private void startMapping(QiContext qiContext) {
        // Here we will map Pepper's surroundings.
        // Create a LocalizeAndMap action.
        localizeAndMap = LocalizeAndMapBuilder.with(qiContext).build();
       // Add an on status changed listener on the LocalizeAndMap action to know when the robot has mapped his environment.
        localizeAndMap.addOnStatusChangedListener(status -> {
            switch (status) {
                case LOCALIZED:
                    Log.e("map", "Robot has mapped his environment.");
                    // Dump the ExplorationMap.
                    explorationMap = localizeAndMap.dumpMap();
                    // Cancel the LocalizeAndMap action.
                    localizationAndMapping.requestCancellation();
                    break;
            }
        });
        Log.e("map", "Mapping...");
        // Execute the LocalizeAndMap action asynchronously.
        localizationAndMapping = localizeAndMap.async().run();

        // Add a lambda to the action execution.
        localizationAndMapping.thenConsume(future -> {
            if (future.hasError()) {
                Log.e("map", "LocalizeAndMap action finished with error.", future.getError());
            } else if (future.isCancelled()) {
                // The LocalizeAndMap action has been cancelled.
                startLocalizing(qiContext);
            }
        });
    }

    private void startLocalizing(QiContext qiContext) {
        // Here we will make Pepper localize himself in the map.
        // Create a Localize action.
        localize = LocalizeBuilder.with(qiContext)
                .withMap(explorationMap)
                .build();
        // Add an on status changed listener on the Localize action to know when the robot is localized in the map.
        localize.addOnStatusChangedListener(status -> {
            switch (status) {
                case LOCALIZED:
                    Log.e("map", "Robot is localized.");
                    startHumanAwareness();
                    break;
            }
        });
        Log.e("map", "Localizing...");
        // Execute the Localize action asynchronously.
        Future<Void> localization = localize.async().run();
        // Add a lambda to the action execution.
        localization.thenConsume(future -> {
            if (future.hasError()) {
                Log.e("map", "Localize action finished with error.", future.getError());
            }
        });
    }

    private void startHumanAwareness() {
        humanAwareness = qiContext.getHumanAwareness();
        try {
            recommendedHuman = humanAwareness.getRecommendedHumanToEngage();
            humanAwareness.addOnRecommendedHumanToEngageChangedListener(this::onRecommendedHuman);
            onRecommendedHuman(recommendedHuman);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void onRecommendedHuman(Human human){
        if(human!=null && state == "STATE_SOLITARY"){
            state = "STATE_ENGAGED";
            EngageHuman engage = humanAwareness.makeEngageHuman(qiContext.getRobotContext(), human);
//            EngageHuman engage = EngageHumanBuilder.with(qiContext).withHuman(human).build();
            engage.async().addOnHumanIsEngagedListener(()-> {
                goToBookmark("init_hi","chat");
                showHomeFragment();
                countDownNoInteraction.start();
            });
            engage.async().addOnHumanIsDisengagingListener(()-> {
//                goToBookmark("init_bye","chat");
                countDownNoInteraction.reset();

            });
            engage.async().run();
            state =  "STATE_SOLITARY";
            onRecommendedHuman(nextHumen);
        }else {
            if(human !=null){
                nextHumen = human;
            }

        }
    }

    private void addCity(final Phrase[] cities) {
        if (cityList != null) {
            cityList.async().addPhrases(Arrays.asList(cities));
        }
    }
    private void addPlace(final Phrase[] places) {
        if (placeList != null) {
            placeList.async().addPhrases(Arrays.asList(places));
        }
    }

    @Override
    public void onRobotFocusLost() {
        if (humanAwareness != null) {
            try {
                humanAwareness.removeAllOnEngagedHumanChangedListeners();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (chat != null) {
            try {
                chat.removeAllOnStartedListeners();
                chat.removeAllOnNormalReplyFoundForListeners();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Remove on status changed listeners from the LocalizeAndMap action.
        if (localizeAndMap != null) {
            localizeAndMap.removeAllOnStatusChangedListeners();
        }
        // Remove on status changed listeners from the Localize action.
        if (localize != null) {
            localize.removeAllOnStatusChangedListeners();
        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        if (chat != null) {
            try {
                chat.removeAllOnStartedListeners();
                chat.removeAllOnNormalReplyFoundForListeners();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showHomeFragment()
    {
        showFragment(new Fragment[]{cityFragment, taxiFragment, restaurantFragment, hotelFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment, taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                homeFragment);

    }
    public void showCityFragment()
    {
        showFragment(new Fragment[]{homeFragment, taxiFragment, restaurantFragment, hotelFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment, taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                cityFragment);
        goToBookmark("init_city", "chat");

    }
    public void showTaxiFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, restaurantFragment, hotelFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                taxiFragment);

    }
    public void showTaxiConfirmFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, restaurantFragment, hotelFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment, taxiFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                taxiConfirmFragment);
        goToBookmark("init_taxi_booking", "chat");

    }


    public void showHotelFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment,restaurantFragment, taxiFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                hotelFragment);
        goToBookmark("init_hotel", "chat");
    }
    public void showHotelDetailFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment,restaurantFragment, taxiFragment, placeDetailFragment,
                        placeFragment, hotelFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                hotelDetailFragment);
        setQiVariable("hotel_description", hotelFragment.myDescription);
        goToBookmark("init_hotel_detail", "chat");



    }
    public void showRestaurantFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, placeDetailFragment,
                        placeFragment, hotelDetailFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                restaurantFragment);
        goToBookmark("init_services","chat");
    }
    public void showPlaceDetailFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeFragment, hotelDetailFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                placeDetailFragment);
        setQiVariable("place_description", placeDTOtmp.newPlaceDescription);
        goToBookmark("init_place_detail", "chat");

    }
    public void showPlaceFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeDetailFragment, hotelDetailFragment,taxiConfirmFragment, laundryFragment,roomFragment,
                        splashScreenFragment, cleaningFragment},
                placeFragment);
        goToBookmark("init_place", "chat");

    }
    public void showRoomFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeDetailFragment, hotelDetailFragment,taxiConfirmFragment, placeFragment,laundryFragment,
                        splashScreenFragment, cleaningFragment},
                roomFragment);
        setQiVariable("hotel_description", hotelFragment.myDescription.substring(0,130));
        goToBookmark("init_hotel_detail", "chat");

    }
    public void showSplashScreenFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeDetailFragment, hotelDetailFragment,taxiConfirmFragment, placeFragment,laundryFragment,
                        roomFragment, cleaningFragment},
                splashScreenFragment);

    }
    public void showLaundryFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeDetailFragment, hotelDetailFragment,taxiConfirmFragment, placeFragment,cleaningFragment,
                        roomFragment, splashScreenFragment},
                laundryFragment);
        goToBookmark("init_roomnumber", "chat");
    }
    public void showCleaningFragment()
    {
        showFragment(new Fragment[]{homeFragment, cityFragment, taxiFragment, hotelFragment, restaurantFragment,
                        placeDetailFragment, hotelDetailFragment,taxiConfirmFragment, placeFragment,laundryFragment,
                        roomFragment, splashScreenFragment},
                cleaningFragment);
        goToBookmark("init_roomnumber", "chat");
    }
    public void GoHomeFragment(){
        runOnUiThread(()-> {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment instanceof HomeFragment) {
                    continue;
                }
                else {
                    fragmentManager.popBackStackImmediate();
                }
            }
            showHomeFragment();
        });
    }
    public void GoBackFragment(){
        runOnUiThread(()->{
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_layout);
                if (currentFragment instanceof CityFragment)
                    showCityFragment();
                if (currentFragment instanceof TaxiFragment)
                    showHomeFragment();
                if (currentFragment instanceof RestaurantFragment)
                    showRestaurantFragment();
                if (currentFragment instanceof HotelFragment)
                    showHotelFragment();
                if (currentFragment instanceof PlaceFragment)
                    showPlaceFragment();
                if (currentFragment instanceof PlaceDetailFragment)
                    showPlaceDetailFragment();
                if (currentFragment instanceof HotelDetailFragment)
                    showPlaceDetailFragment();
                if (currentFragment instanceof RoomFragment)
                    showRoomFragment();
            }
        });
    }

    public void GetPlaceById(int placeId){
        placeDTOtmp = placeFragment.GetPlaceDTOById(placeId);
        placeDetailFragment.setPlaceDto(placeDTOtmp);
    }


    public void goToBookmark(String bookmark, String topic) {
        Log.d("TAG", "going to bookmark " + bookmark + " in topic : " + topic);
        if (!bookmark.equals("")) {
            Log.d("TAG", "going to bookmark " + bookmark + " in topic : " + topic);
            Map<String, Bookmark> tmp = bookmarks.get(topic);

            cancelcurrentGotoBookmarkFuture().thenConsume(uselessFuture ->
                    currentGotoBookmarkFuture =qiChatbot.async().goToBookmark(tmp.get(bookmark),
                            AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE));
        }
    }

    public Future<Void> cancelcurrentGotoBookmarkFuture() {
        if (currentGotoBookmarkFuture == null) return Future.of(null);
        currentGotoBookmarkFuture.cancel(true);
        return currentGotoBookmarkFuture;
    }

    /**
     * updates the value of the qiVariable
     *
     * @param variableName the name of the variable
     * @param value        the value that needs to be set
     */

    public void setQiVariable(String variableName, String value) {
//        Log.d("TAG", "size va : " + variables.size());
        variables.get(variableName).async().setValue(value);
    }

    public void back_button(View v)
    {
        back_btn = v.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBackFragment();
            }
        });
    }

    public void home_button(View v)
    {
        home_btn = v.findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoHomeFragment();
            }
        });
    }

    @Override
    protected void onDestroy() {
        countDownNoInteraction.cancel();
        QiSDK.unregister(this,this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        countDownNoInteraction.cancel();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (chat != null) {
            try {
                chat.removeAllOnStartedListeners();
                chat.removeAllOnNormalReplyFoundForListeners();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}