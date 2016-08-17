package com.example.wwsskkaa.curiomobile.dummy;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.wwsskkaa.curiomobile.ItemListActivity;
import com.example.wwsskkaa.curiomobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private static class GetJsonTask extends AsyncTask<URL, Integer, JSONArray> {
        protected JSONArray doInBackground(URL... urls) {
            JSONArray jsonObject=null;
            URL url = urls[0];

            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = null;

                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                {responseStrBuilder.append(inputStr);}

                jsonObject = new JSONArray(responseStrBuilder.toString());



            } catch (Exception e) {
                e.printStackTrace();
            }
            //        readStream(in);
            finally {

                urlConnection.disconnect();
            }

            return jsonObject;
        }

        protected void onProgressUpdate(Integer... progress) {
            //  setProgressPercent(progress[0]);
        }

        protected void onPostExecute(JSONArray result) {

            try {
                if(result!=null)
                {
                    removeitems();
                for (int i = 0; i < result.length(); i++) {
                    String id = "" + (i + 1);
                    DummyItem newdum = new DummyItem(id, result.getJSONObject(i).get("name").toString(), result.getJSONObject(i).get("description").toString());
                    newdum.setBrief(result.getJSONObject(i).get("short_description").toString());
                    JSONArray teamarray = result.getJSONObject(i).getJSONArray("team");
                    if (teamarray.length() > 0) {
                        for (int j = 0; j < teamarray.length(); j++) {
                            newdum.addTeam(Integer.parseInt(teamarray.getJSONObject(j).get("id").toString()));
                        }
                    }
                    JSONArray contributionarray = result.getJSONObject(i).getJSONArray("curios");

                    if (contributionarray.length() > 0) {
                        for (int j = 0; j < contributionarray.length(); j++) {
                            newdum.addContri(contributionarray.getInt(j));
                        }

                    }


//                    for(int j=0;j<teamarray.length();j++)
//                    {
//                        newdum.addMember(teamarray.getJSONObject(j).get("owner").toString(),Integer.parseInt(teamarray.getJSONObject(j).get("id").toString()));
//                    }
                    // ITEMS.add(newdum);
                    // ITEM_MAP.put("26", newdum);
                    addItem(newdum);
                    new DownloadImageTask(newdum).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,result.getJSONObject(i).get("avatar").toString());

                    URL urlurl = null;
                    try {
                        urlurl = new URL("http://test.crowdcurio.com/api/curio/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //new DownloadContribution(newdum).execute(urlurl);
                    new DownloadContribution(newdum).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,urlurl);

                    URL urlurl1 = null;
                    try {
                        urlurl1 = new URL("http://test.crowdcurio.com/api/user/profile/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                  //  new DownloadTeam(newdum).execute(urlurl1);
                    new DownloadTeam(newdum).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,urlurl1);

                   // new DownloadImageTask(newdum).execute(result.getJSONObject(i).get("avatar").toString());


                }
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        DummyItem _dm;

        public DownloadImageTask(DummyItem dm) {
            this._dm = dm;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            _dm.setBackground(result);
        }
    }

    private static class DownloadContribution extends AsyncTask<URL, Void, JSONArray> {
        DummyItem _dm;

        public DownloadContribution(DummyItem dm) {
            this._dm = dm;
        }

        protected JSONArray doInBackground(URL... urls) {
            JSONArray jsonObject=null;
            URL url = urls[0];

            HttpURLConnection urlConnection = null;

            try {
                //url = new URL("http://test.crowdcurio.com/api/project/");
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = null;

                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                {responseStrBuilder.append(inputStr);}

                jsonObject = new JSONArray(responseStrBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            //        readStream(in);

            return jsonObject;
        }

        protected void onPostExecute(JSONArray result) {
            if(result!=null)
            {
                if(_dm.contributionlist.size()>0)
                {
                    String conti="";
                    for(int i=0;i<_dm.contributionlist.size();i++) {
                        try {
                            for(int j=0;j<result.length();j++) {
                                if(Integer.parseInt(result.getJSONObject(j).get("id").toString())==_dm.contributionlist.get(i)) {
                                   // conti += "1.Title:  " + result.getJSONObject(_dm.contributionlist.get(i) - 1).get("title").toString() + "\n\n";
                                    //conti += "2.Question:  " + result.getJSONObject(_dm.contributionlist.get(i) - 1).get("question").toString() + "\n\n";
                                    //conti += "3.Motivation:  " + result.getJSONObject(_dm.contributionlist.get(i) - 1).get("motivation").toString() + "\n\n";
                                    conti += "1.Title:  " + result.getJSONObject(j).get("title").toString() + "\n\n";
                                    conti += "2.Question:  " + result.getJSONObject(j).get("question").toString() + "\n\n";
                                    conti += "3.Motivation:  " + result.getJSONObject(j).get("motivation").toString() + "\n\n";
                                    conti += "~~~~~~~~~~~~~~~~\n\n";
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    _dm.setConti(conti);
                }
            }

        }
    }


    private static class DownloadTeam extends AsyncTask<URL, Void, JSONObject> {
        DummyItem _dm;

        public DownloadTeam(DummyItem dm) {
            this._dm = dm;
        }

        protected JSONObject doInBackground(URL... urls) {


            JSONObject jsonObject=null;

            URL url = urls[0];

            HttpURLConnection urlConnection = null;

            try {
                //url = new URL("http://test.crowdcurio.com/api/project/");
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = null;

                in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                {responseStrBuilder.append(inputStr);}

                //jsonObject = new JSONObject(responseStrBuilder.toString());
                jsonObject = new JSONObject(responseStrBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            finally {
                urlConnection.disconnect();
            }
            //        readStream(in);

            return jsonObject;
        }

        protected void onPostExecute(JSONObject result) {

            if(result!=null)
            {

                if(_dm.namelist.size()>0)
                {
                    String conti="";
                    for(int i=0;i<_dm.namelist.size();i++) {
                        try {
//

                            JSONArray mem=result.getJSONArray("results");
                            if(mem.length()>0) {
                                String nickname=mem.getJSONObject(_dm.namelist.get(i) - 1).get("nickname").toString();
                                String email=mem.getJSONObject(_dm.namelist.get(i)-1).get("email").toString();
                                String bio=mem.getJSONObject(_dm.namelist.get(i)-1).get("bio").toString();
                                String title=mem.getJSONObject(_dm.namelist.get(i)-1).get("title").toString();
                                DummyItem.Teammember newmember=new DummyItem.Teammember(nickname,email,bio,title);
                                _dm.addTm(newmember);
                                new DownloadPhotoTask(newmember)
                                        .execute(mem.getJSONObject(_dm.namelist.get(i)-1).get("avatar").toString());
                                conti += "Nickname:  " + nickname + "\n\n";
                                conti+="Email:  "+email+"\n\n";
                                conti+="Bio:  "+bio+"\n\n";
                                conti+="Title:  "+title+"\n\n";
                            }
                            conti+="~~~~~~~~~~~~~~~~\n\n";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    _dm.setTeam(conti);
                }
            }

        }
    }

    private static class DownloadPhotoTask extends AsyncTask<String, Void, Bitmap> {
        DummyItem.Teammember mem;

        public DownloadPhotoTask(DummyItem.Teammember mem) {
            this.mem = mem;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            mem.setPhoto(result);
        }
    }


    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.

        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
//        URL apiurl = null;
//        try {
//            apiurl = new URL("http://test.crowdcurio.com/api/project/");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        new GetJsonTask().execute(apiurl);
        Execution();
    }
    public static void Execution()
    {
        URL apiurl = null;
        try {
            apiurl = new URL("http://test.crowdcurio.com/api/project/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetJsonTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiurl);
       // new GetJsonTask().execute(apiurl);
    }

    public static void searchExecution(String area,String searchword)
    {
        //"http://test.crowdcurio.com/api/project/"
//        searchword="http://test.crowdcurio.com/api/project/?description="+searchword;
//        URL apiurl = null;
//        try {
//            apiurl = new URL(searchword);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        new GetJsonTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiurl);
        searchword="http://test.crowdcurio.com/api/project/?"+area+"="+searchword;
       // searchword="http://test.crowdcurio.com/api/project/?name="+searchword;
        URL apiurl = null;
        try {
            apiurl = new URL(searchword);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GetJsonTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiurl);
    }



    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public static void removeitems() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item*** " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }



    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public static class Teammember{
            public String nickname;
            public String email;
            public String bio;
            public String title;
            public Bitmap photo;

            public Teammember(String name,String email,String bio,String title){
                this.nickname=name;
                this.email=email;
                this.bio=bio;
                this.title=title;
                photo=null;
            }
            public void setPhoto(Bitmap p)
            {
                photo=p;
            }

            public String toString()
            {
                String conti="";

                conti += "Nickname:  " + nickname + "\n\n";
                conti+="Email:  "+email+"\n\n";
                conti+="Bio:  "+bio+"\n\n";
                conti+="Title:  "+title+"\n\n";
                conti+="~~~~~~~~~~~~~~~~\n\n";

                return conti;
            }

        }
        public ArrayList<Teammember> tm;
        public String id;
        public  String content="[Un-named Projects]";
        public  String details="[No Details Yet]";
        public String brief="";
        public Bitmap backgroundbit;
        public ArrayList<Integer> namelist;
        public ArrayList<Integer> contributionlist;
        public String conti;
        public String team;



        public DummyItem(String id, String content, String details) {
            this.id=id;
            if(content.length()!=0) {
                this.content = content;
            }
            if(details.length()!=0) {
                this.details = details;
            }
           // this.content = content;
            //this.details = details;
            namelist=new ArrayList<Integer>();
            contributionlist=new ArrayList<Integer>();
            conti="[No Questions Yet]";
            team="";
            tm=new ArrayList<Teammember>();

            // tm= new ArrayList<Teammember>();
        }
//        public void addMember(String name,int id)
//        {
//            Teammember newmember=new Teammember(name,id);
//            tm.add(newmember);
//        }
//        public String getTm() {
//            String result="";
//            for (int i = 0; i < tm.size(); i++) {
//            result+= tm.get(i).toString();
//            }
//            return result;
//        }

        public void setBrief(String br)
        {
            if(br.length()!=0){brief=br;}
            else{brief="[No Brief Yet]";}
        }
        public String getBrief()
        {
            return brief;
        }
        public void setBackground(Bitmap b) {
            if(b!=null)
            {
            }
                this.backgroundbit = b;
        }
        public void addContri(int e){contributionlist.add(e);}
        public void addTeam(int e){namelist.add(e);}
        public void addTm(Teammember t){tm.add(t);}
        public void setConti(String c){conti=c;}
        public Bitmap getBackground()
        {
            return backgroundbit;
        }
        public void setTeam(String t){team=t;}
        public String namesToString()
        {
            String returnname="";
            for(int i=0;i<namelist.size();i++)
            {
                returnname+=namelist.get(i)+"\n";
            }
            return returnname;
        }
//        public void setTeamarray(JSONArray ta)
//        {
//            teamarray=ta;
//        }


        @Override
        public String toString() {
            return content;
        }
    }
}
