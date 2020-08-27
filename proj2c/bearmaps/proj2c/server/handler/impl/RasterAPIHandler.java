package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import org.junit.Test;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;
import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;
import static org.junit.Assert.assertEquals;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        System.out.println("Since you haven't implemented RasterAPIHandler.processRequest, nothing is displayed in "
                + "your browser.");
        int depth = depthOfResult(requestParams);

        double longPT = longOfOneTile(depth);
        double latiPT = latOfOneTile(depth);

        double ullonOfRaster = ullonOfRaster(requestParams.get("ullon"), longPT);
        double ullatOfRaster = ullatOfRaster(requestParams.get("ullat"), latiPT);

        int numOfTilesLongiRound = numOfRequiredTiles(ullonOfRaster, requestParams.get("lrlon"), longPT, "long");
        int numOfTilesLatiRound = numOfRequiredTiles(ullatOfRaster, requestParams.get("lrlat"), latiPT, "lati");

        double lrlonOfRaster = ullonOfRaster + numOfTilesLongiRound * longPT;
        double lrlatOfRaster = ullatOfRaster - numOfTilesLatiRound * latiPT;

        int xStart = xOfUlRaster(requestParams.get("ullon"), longPT);
        int yStart = yOfUlRaster(requestParams.get("ullat"), latiPT);

        String[][] render_grid = generateGrid(xStart, yStart, numOfTilesLongiRound, numOfTilesLatiRound, depth);

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", ullonOfRaster);
        results.put("raster_ul_lat", ullatOfRaster);
        results.put("raster_lr_lon", lrlonOfRaster);
        results.put("raster_lr_lat", lrlatOfRaster);
        results.put("query_success", true);
        results.put("depth", depth);

        return results;
    }

    //calculate depth for the query
    private static int depthOfResult(Map<String, Double> requestParams) {
        Double lonDPP = (requestParams.get("lrlon") - requestParams.get("ullon")) / requestParams.get("w") * 288200;
        Double depth0LonDPP = (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / Constants.TILE_SIZE * 288200;
        double depth = Math.ceil(Math.log(depth0LonDPP / lonDPP) / Math.log(2));
        return Math.min((int)depth, 7);
    }


    //compute the bounding box for a given filename
    private static Map<String, Double> boundingBox(String filename) {
        Map<String, Double> boundingBox = new HashMap<>();
        int depth = Character.getNumericValue(filename.charAt(1));
        int x = Character.getNumericValue(filename.charAt(4));
        int y = Character.getNumericValue(filename.charAt(7));

        double longi = longOfOneTile(depth);
        double ullon = Constants.ROOT_ULLON + longi * x;
        double lrlon = ullon + longi;

        double lati = latOfOneTile(depth);
        double ullat = Constants.ROOT_ULLAT - lati * y;
        double lrlat = ullat - lati;

        boundingBox.put("ullon", ullon);
        boundingBox.put("lrlon", lrlon);
        boundingBox.put("ullat", ullat);
        boundingBox.put("lrlat", lrlat);

        return boundingBox;
    }

    private static double longOfOneTile(int depth){
        int numOfTilesInOneSide = (int) Math.pow(2, depth);
        return (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / numOfTilesInOneSide;
    }

    private static double latOfOneTile(int depth) {
        int numOfTilesInOneSide = (int) Math.pow(2, depth);
        return (Constants.ROOT_ULLAT - Constants.ROOT_LRLAT) / numOfTilesInOneSide;
    }

    private static int xOfUlRaster(double requestUllon, double longiOfPerTile) {
        return Math.max(0, (int) Math.floor((requestUllon - Constants.ROOT_ULLON) / longiOfPerTile));
    }

    private static double ullonOfRaster(double requestUllon, double longiOfPerTile) {
        int xOfRaster = xOfUlRaster(requestUllon, longiOfPerTile);
        return Constants.ROOT_ULLON + xOfRaster * longiOfPerTile;
    }

    private static int yOfUlRaster(double requestUllat, double latiOfPerTile) {
        return Math.max(0, (int) Math.floor((Constants.ROOT_ULLAT - requestUllat) / latiOfPerTile));
    }

    private static double ullatOfRaster(double requestUllat, double latiOfPerTile) {
        int yOfRaster = yOfUlRaster(requestUllat, latiOfPerTile);
        return Constants.ROOT_ULLAT - yOfRaster * latiOfPerTile;
    }

    //how many tiles required
    private static int numOfRequiredTiles(double rasterUl, double requestLr, double lenOfPT, String latiOrLong) {
        double validateLen;
        if (latiOrLong.equals("long")) {
            validateLen = Math.abs(Math.min(Constants.ROOT_LRLON, requestLr) - rasterUl);
        } else {
            validateLen = Math.abs(Math.max(Constants.ROOT_LRLAT, requestLr) - rasterUl);
        }
        return (int) Math.ceil(validateLen / lenOfPT);
    }

    private static String[][] generateGrid(int StartX, int StartY, int numX, int numY, int depth) {
        String[][] grids = new String[numY][numX];
        int y = StartY;
        for (int i = 0; i < numY; i++) {
            int x = StartX;
            for (int j = 0; j < numX; j++) {
                grids[i][j] = "d" + depth + "_" + "x" + x + "_" + "y" + y +".png";
                x++;
            }
            y++;
        }
        return grids;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }

    private Map<String, Double> generateParams() {
        Map<String, Double> params = new HashMap<>();
        params.put("ullon", -122.241632);
        params.put("ullat", 37.87655);
        params.put("lrlon", -122.24053);
        params.put("lrlat", 37.87548);
        params.put("w", 892.0);
        params.put("h", 875.0);
        return params;
    }

    @Test
    public void depthTest() {
        Map<String, Double> params = generateParams();
        int act = RasterAPIHandler.depthOfResult(params);
        int exp = 7;
        assertEquals(exp, act);
    }

    @Test
    public void StartPointNumTilesTest() {
        Map<String, Double> params = generateParams();
        int x = xOfUlRaster(params.get("ullon"), longOfOneTile(7));
        int y = yOfUlRaster(params.get("ullat"), latOfOneTile(7));
        assertEquals(84, x);
        assertEquals(28, y);

        double ullon = ullonOfRaster(params.get("ullon"), longOfOneTile(7));
        double ullat = ullatOfRaster(params.get("ullat"), latOfOneTile(7));
        System.out.println("lon"+ullon);
        System.out.println("lat"+ullat);

        int numlong = numOfRequiredTiles(ullon, params.get("lrlon"), longOfOneTile(7), "long");
        int numlat = numOfRequiredTiles(ullat, params.get("lrlat"), latOfOneTile(7), "lati");
        assertEquals(numlong, 3);
        assertEquals(numlat, 3);
    }

    @Test
    public void gridsTest() {
        String[][] r = generateGrid(84, 28, 3, 3, 7);
        for (int i = 0; i < r.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                System.out.print(r[i][j] + " ");
            }
            System.out.println();
        }
    }

}
