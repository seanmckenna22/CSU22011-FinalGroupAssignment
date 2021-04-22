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

        this.tripId = tripId.trim();
        this.departureTime = departureTime.trim();
        this.stopId = stopId.trim();
        this.stopSequence = stopSequence.trim();
        this.stopHeadSign = stopHeadSign.trim();
        this.pickUpTypes =  pickUpTypes.trim();
        this.dropOffType = dropOffType.trim();
        this.distanceTravelled = distanceTravelled.trim();

    }

}
