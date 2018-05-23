package se.steam.trellov2.service.exception;

import javax.ws.rs.ext.Provider;

@Provider
public final class TeamCapacityReachedException extends RuntimeException {

    public TeamCapacityReachedException(String s) {
        super(s);
    }
}
