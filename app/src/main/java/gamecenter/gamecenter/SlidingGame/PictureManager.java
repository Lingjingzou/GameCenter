package gamecenter.gamecenter.SlidingGame;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import android.content.Context;

import gamecenter.gamecenter.R;

class PictureManager {
    @SuppressLint("UseSparseArrays")
    private HashMap< Integer, Bitmap> picturePieces = new HashMap<>();

    private HashMap< String, Bitmap> pictures = new HashMap<>();

    private Context context;

    PictureManager(Context context){
        this.context = context;
    }

    /**
     * Return the pieces of the background.
     * @return HashMap<String, Bitmap>
     */
    HashMap<Integer, Bitmap> getPicturePieces(){return picturePieces;}

    /**
     * Record the picture.
     * @param picture Bitmap
     * @param picName String
     */
    void addPicture(Bitmap picture, String picName){pictures.put(picName, picture);}

    /**
     * Split the picture into pieces depends on the size that user choose.
     * @param pictureName String
     * @param level int
     */
    void splitPicture(String pictureName, int level){
        Bitmap picture = pictures.get(pictureName);
        Bitmap picturePiece;
        @SuppressLint("UseSparseArrays") HashMap<Integer, Bitmap> picturePieces = new HashMap<>();
        assert picture != null;
        int itemWidth = picture.getWidth() / level;
        int itemHeight = picture.getHeight() / level;

        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                picturePiece = Bitmap.createBitmap(picture, (j - 1) * itemWidth, (i - 1) * itemHeight, itemWidth, itemHeight);
                picturePieces.put(picturePieces.size(), picturePiece);// "id": picturePiece
            }
        }

        picturePieces.remove(level * level - 1);

        Bitmap blankPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_16);
        blankPiece = Bitmap.createBitmap(blankPiece, 0, 0, itemWidth, itemHeight);
        picturePieces.put(picturePieces.size(),blankPiece);
        this.picturePieces = picturePieces;
    }

    /**
     * Return the full picture for user to check.
     * @param picName String
     * @return Bitmap
     */
    Bitmap getExactPicture(String picName){return pictures.get(picName);}

}
