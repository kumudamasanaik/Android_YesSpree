package com.yesspree.app.modelapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parcelable {

    @Expose
    @SerializedName("sub")
    private List<Category> sub;
    @Expose
    @SerializedName("updated")
    private String updated;
    @Expose
    @SerializedName("block")
    private String block;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("pic")
    private String pic;
    @Expose
    @SerializedName("parent")
    private String parent;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("_id")
    private String _id;

    boolean isSelectedCategory = false;


    public List<Category> getSub() {
        return sub;
    }

    public void setSub(List<Category> sub) {
        this.sub = sub;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isSelectedCategory() {
        return isSelectedCategory;
    }

    public void setSelectedCategory(boolean selectedCategory) {
        isSelectedCategory = selectedCategory;
    }






    /*parcelble */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.sub);
        dest.writeString(this.updated);
        dest.writeString(this.block);
        dest.writeString(this.description);
        dest.writeString(this.pic);
        dest.writeString(this.parent);
        dest.writeString(this.name);
        dest.writeString(this._id);
        dest.writeByte(this.isSelectedCategory ? (byte) 1 : (byte) 0);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.sub = new ArrayList<Category>();
        in.readList(this.sub, Category.class.getClassLoader());
        this.updated = in.readString();
        this.block = in.readString();
        this.description = in.readString();
        this.pic = in.readString();
        this.parent = in.readString();
        this.name = in.readString();
        this._id = in.readString();
        this.isSelectedCategory = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public static String dummy = "[\n" +
            "            {\n" +
            "                \"_id\": \"1\",\n" +
            "                \"name\": \"Personal Care\",\n" +
            "                \"parent\": \"0\",\n" +
            "                \"pic\": \"uploads/category/15242049030grocery.png\",\n" +
            "                \"description\": \"\",\n" +
            "                \"block\": \"0\",\n" +
            "                \"updated\": \"2018-04-20 11:45:15\",\n" +
            "                \"sub\": [\n" +
            "                    {\n" +
            "                        \"_id\": \"6\",\n" +
            "                        \"name\": \"Soap\",\n" +
            "                        \"parent\": \"1\",\n" +
            "                        \"pic\": \"uploads/category/15242049990grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:46:55\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"7\",\n" +
            "                        \"name\": \"Hand Wash\",\n" +
            "                        \"parent\": \"1\",\n" +
            "                        \"pic\": \"uploads/category/15242050020grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:46:55\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"8\",\n" +
            "                        \"name\": \"Skin Care\",\n" +
            "                        \"parent\": \"1\",\n" +
            "                        \"pic\": \"uploads/category/15242050070grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:46:55\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"9\",\n" +
            "                        \"name\": \"Talc\",\n" +
            "                        \"parent\": \"1\",\n" +
            "                        \"pic\": \"uploads/category/15242050120grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:46:55\",\n" +
            "                        \"sub\": []\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"_id\": \"2\",\n" +
            "                \"name\": \"Grocery\",\n" +
            "                \"parent\": \"0\",\n" +
            "                \"pic\": \"uploads/category/15242049000grocery.png\",\n" +
            "                \"description\": \"\",\n" +
            "                \"block\": \"0\",\n" +
            "                \"updated\": \"2018-04-20 11:45:15\",\n" +
            "                \"sub\": [\n" +
            "                    {\n" +
            "                        \"_id\": \"10\",\n" +
            "                        \"name\": \"Flours\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050670grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 12:07:20\",\n" +
            "                        \"sub\": [\n" +
            "                            {\n" +
            "                                \"_id\": \"32\",\n" +
            "                                \"name\": \"Cake Flour\",\n" +
            "                                \"parent\": \"10\",\n" +
            "                                \"pic\": \"uploads/category/15242054110grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:53:42\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"33\",\n" +
            "                                \"name\": \"Barley Flour\",\n" +
            "                                \"parent\": \"10\",\n" +
            "                                \"pic\": \"uploads/category/15242054160grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:53:42\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"34\",\n" +
            "                                \"name\": \"White Rice Flour\",\n" +
            "                                \"parent\": \"10\",\n" +
            "                                \"pic\": \"uploads/category/15242054190grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:53:42\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"35\",\n" +
            "                                \"name\": \"Whole Wheat Flour\",\n" +
            "                                \"parent\": \"10\",\n" +
            "                                \"pic\": \"uploads/category/15242054130grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:53:43\",\n" +
            "                                \"sub\": [\n" +
            "                                    {\n" +
            "                                        \"_id\": \"37\",\n" +
            "                                        \"name\": \"Chakki Atta\",\n" +
            "                                        \"parent\": \"35\",\n" +
            "                                        \"pic\": \"uploads/category/15242055530grocery.png\",\n" +
            "                                        \"description\": \"\",\n" +
            "                                        \"block\": \"0\",\n" +
            "                                        \"updated\": \"2018-04-20 11:55:59\",\n" +
            "                                        \"sub\": []\n" +
            "                                    },\n" +
            "                                    {\n" +
            "                                        \"_id\": \"38\",\n" +
            "                                        \"name\": \"Multigrain Atta\",\n" +
            "                                        \"parent\": \"35\",\n" +
            "                                        \"pic\": \"uploads/category/15242055500grocery.png\",\n" +
            "                                        \"description\": \"\",\n" +
            "                                        \"block\": \"0\",\n" +
            "                                        \"updated\": \"2018-04-20 11:55:59\",\n" +
            "                                        \"sub\": []\n" +
            "                                    },\n" +
            "                                    {\n" +
            "                                        \"_id\": \"39\",\n" +
            "                                        \"name\": \"Wheat Atta\",\n" +
            "                                        \"parent\": \"35\",\n" +
            "                                        \"pic\": \"uploads/category/15242055580grocery.png\",\n" +
            "                                        \"description\": \"\",\n" +
            "                                        \"block\": \"0\",\n" +
            "                                        \"updated\": \"2018-04-20 11:55:59\",\n" +
            "                                        \"sub\": []\n" +
            "                                    },\n" +
            "                                    {\n" +
            "                                        \"_id\": \"40\",\n" +
            "                                        \"name\": \"Chakki Wheat Atta\",\n" +
            "                                        \"parent\": \"35\",\n" +
            "                                        \"pic\": \"uploads/category/15242055550grocery.png\",\n" +
            "                                        \"description\": \"\",\n" +
            "                                        \"block\": \"0\",\n" +
            "                                        \"updated\": \"2018-04-20 11:55:59\",\n" +
            "                                        \"sub\": []\n" +
            "                                    }\n" +
            "                                ]\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"36\",\n" +
            "                                \"name\": \"All-Purpose Flour\",\n" +
            "                                \"parent\": \"10\",\n" +
            "                                \"pic\": \"uploads/category/15242054210grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:53:43\",\n" +
            "                                \"sub\": []\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"11\",\n" +
            "                        \"name\": \"Rice\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050600grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:47:56\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"12\",\n" +
            "                        \"name\": \"Masala & Spices\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050720grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:47:56\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"13\",\n" +
            "                        \"name\": \"Instant Mix\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050700grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:47:56\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"14\",\n" +
            "                        \"name\": \"Dry Fruits\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050750grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:47:56\",\n" +
            "                        \"sub\": [\n" +
            "                            {\n" +
            "                                \"_id\": \"28\",\n" +
            "                                \"name\": \"Badam\",\n" +
            "                                \"parent\": \"14\",\n" +
            "                                \"pic\": \"uploads/category/15242053160grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:52:08\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"29\",\n" +
            "                                \"name\": \"Pista\",\n" +
            "                                \"parent\": \"14\",\n" +
            "                                \"pic\": \"uploads/category/15242053220grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:52:08\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"30\",\n" +
            "                                \"name\": \"Cashew nut\",\n" +
            "                                \"parent\": \"14\",\n" +
            "                                \"pic\": \"uploads/category/15242053260grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:52:08\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"31\",\n" +
            "                                \"name\": \"Fig\",\n" +
            "                                \"parent\": \"14\",\n" +
            "                                \"pic\": \"uploads/category/15242053190grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:52:08\",\n" +
            "                                \"sub\": []\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"15\",\n" +
            "                        \"name\": \"Dal and Pulses\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15242050640grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:47:56\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"41\",\n" +
            "                        \"name\": \"products available\",\n" +
            "                        \"parent\": \"2\",\n" +
            "                        \"pic\": \"uploads/category/15256703990stay-healthy-sign.jpeg\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-05-07 10:50:01\",\n" +
            "                        \"sub\": [\n" +
            "                            {\n" +
            "                                \"_id\": \"42\",\n" +
            "                                \"name\": \"SWEETENERS\",\n" +
            "                                \"parent\": \"41\",\n" +
            "                                \"pic\": \"uploads/category/152567044501.jpg\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-05-07 10:51:32\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"43\",\n" +
            "                                \"name\": \"BISCUITS\",\n" +
            "                                \"parent\": \"41\",\n" +
            "                                \"pic\": \"uploads/category/152567045101992.jpg\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-05-07 10:51:32\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"44\",\n" +
            "                                \"name\": \"FRESH CREAM\",\n" +
            "                                \"parent\": \"41\",\n" +
            "                                \"pic\": \"uploads/category/152567047101224.jpg\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-05-07 10:51:32\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"45\",\n" +
            "                                \"name\": \"CEREALS\",\n" +
            "                                \"parent\": \"41\",\n" +
            "                                \"pic\": \"uploads/category/152567049004445.jpg\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-05-07 10:51:32\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"46\",\n" +
            "                                \"name\": \"JAMS\",\n" +
            "                                \"parent\": \"41\",\n" +
            "                                \"pic\": \"uploads/category/15256704580951.jpg\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-05-07 10:51:32\",\n" +
            "                                \"sub\": []\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"_id\": \"3\",\n" +
            "                \"name\": \"Men&#39;s Care\",\n" +
            "                \"parent\": \"0\",\n" +
            "                \"pic\": \"uploads/category/15242049060grocery.png\",\n" +
            "                \"description\": \"\",\n" +
            "                \"block\": \"0\",\n" +
            "                \"updated\": \"2018-04-20 11:45:15\",\n" +
            "                \"sub\": [\n" +
            "                    {\n" +
            "                        \"_id\": \"16\",\n" +
            "                        \"name\": \"Razor\",\n" +
            "                        \"parent\": \"3\",\n" +
            "                        \"pic\": \"uploads/category/15242051330grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:48:59\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"17\",\n" +
            "                        \"name\": \"Shaving Cream, Gel & Foam\",\n" +
            "                        \"parent\": \"3\",\n" +
            "                        \"pic\": \"uploads/category/15242051380grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:48:59\",\n" +
            "                        \"sub\": []\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"18\",\n" +
            "                        \"name\": \"Blades and Cartridge\",\n" +
            "                        \"parent\": \"3\",\n" +
            "                        \"pic\": \"uploads/category/15242051360grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:48:59\",\n" +
            "                        \"sub\": []\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"_id\": \"4\",\n" +
            "                \"name\": \"Women&#39;s Care\",\n" +
            "                \"parent\": \"0\",\n" +
            "                \"pic\": \"uploads/category/15242049090grocery.png\",\n" +
            "                \"description\": \"\",\n" +
            "                \"block\": \"0\",\n" +
            "                \"updated\": \"2018-04-20 11:45:15\",\n" +
            "                \"sub\": []\n" +
            "            },\n" +
            "            {\n" +
            "                \"_id\": \"5\",\n" +
            "                \"name\": \"Baby Care\",\n" +
            "                \"parent\": \"0\",\n" +
            "                \"pic\": \"uploads/category/15242049120grocery.png\",\n" +
            "                \"description\": \"\",\n" +
            "                \"block\": \"0\",\n" +
            "                \"updated\": \"2018-04-20 11:45:15\",\n" +
            "                \"sub\": [\n" +
            "                    {\n" +
            "                        \"_id\": \"19\",\n" +
            "                        \"name\": \"Himalaya baby Products\",\n" +
            "                        \"parent\": \"5\",\n" +
            "                        \"pic\": \"uploads/category/15242051990grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:50:04\",\n" +
            "                        \"sub\": [\n" +
            "                            {\n" +
            "                                \"_id\": \"21\",\n" +
            "                                \"name\": \"Baby Soap\",\n" +
            "                                \"parent\": \"19\",\n" +
            "                                \"pic\": \"uploads/category/15242052390grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:50:49\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"22\",\n" +
            "                                \"name\": \"Baby Shampoo\",\n" +
            "                                \"parent\": \"19\",\n" +
            "                                \"pic\": \"uploads/category/15242052420grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:50:49\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"23\",\n" +
            "                                \"name\": \"Baby Lotion & Cream\",\n" +
            "                                \"parent\": \"19\",\n" +
            "                                \"pic\": \"uploads/category/15242052450grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:50:49\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"24\",\n" +
            "                                \"name\": \"Baby Hair & Massage Oils\",\n" +
            "                                \"parent\": \"19\",\n" +
            "                                \"pic\": \"uploads/category/15242052470grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:50:49\",\n" +
            "                                \"sub\": []\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_id\": \"20\",\n" +
            "                        \"name\": \"Johnson baby Products\",\n" +
            "                        \"parent\": \"5\",\n" +
            "                        \"pic\": \"uploads/category/15242052030grocery.png\",\n" +
            "                        \"description\": \"\",\n" +
            "                        \"block\": \"0\",\n" +
            "                        \"updated\": \"2018-04-20 11:50:04\",\n" +
            "                        \"sub\": [\n" +
            "                            {\n" +
            "                                \"_id\": \"25\",\n" +
            "                                \"name\": \"Baby Lotion & Cream\",\n" +
            "                                \"parent\": \"20\",\n" +
            "                                \"pic\": \"uploads/category/15242052750grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:51:16\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"26\",\n" +
            "                                \"name\": \"Baby Shampoo\",\n" +
            "                                \"parent\": \"20\",\n" +
            "                                \"pic\": \"uploads/category/15242052720grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:51:16\",\n" +
            "                                \"sub\": []\n" +
            "                            },\n" +
            "                            {\n" +
            "                                \"_id\": \"27\",\n" +
            "                                \"name\": \"Baby Soap\",\n" +
            "                                \"parent\": \"20\",\n" +
            "                                \"pic\": \"uploads/category/15242052700grocery.png\",\n" +
            "                                \"description\": \"\",\n" +
            "                                \"block\": \"0\",\n" +
            "                                \"updated\": \"2018-04-20 11:51:16\",\n" +
            "                                \"sub\": []\n" +
            "                            }\n" +
            "                        ]\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        ]";

}
