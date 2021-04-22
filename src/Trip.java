public class Trip {

    /**
     *  String tripInformation = "Trip Id: " + lineData[0] + "// Departure Time: " + lineData[2] +
     *                         "// Stop Id : " + lineData[3] + "// Stop Sequence: " + lineData[4] + "// Stop headsign: " +
     *                         lineData[5] + "// pickup Type: " + lineData[6] + "// dropoff Type: " + lineData[7] ; //"// shape distance travelled: " + lineData[8]
     */

    String tripId;
    String departureTime;
    String stopId;
    String stopSequence;
    String stopHeadSign;
    String pickUpTypes;
    String dropOffType;
    String distanceTravelled;

    public Trip(String tripId, String departureTime, String stopId, String stopSequence, String stopHeadSign, String pickUpTypes, String dropOffType, String distanceTravelled){

        this.tripId = tripId;
        this.departureTime = departureTime;
        this.stopId = stopId;
        this.stopSequence = stopSequence;
        this.stopHeadSign = stopHeadSign;
        this.pickUpTypes =  pickUpTypes;
        this.dropOffType = dropOffType;
        this.distanceTravelled = distanceTravelled;

    }

}
