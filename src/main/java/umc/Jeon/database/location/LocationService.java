package umc.Jeon.database.location;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.config.exception.BaseResponseStatus;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.database.location.model.PostUserLocationReq;

import java.util.List;

import static umc.Jeon.config.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class LocationService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LocationDao locationDao;

    public Location getUserDefaultLocation(long userId) throws BaseException {
        try {
            Location defaultLocation = locationDao.getUserDefaultLocation(userId);
            return defaultLocation;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public boolean setUserLocation(long userId, PostUserLocationReq postUserLocationReq) throws BaseException{
        try {
            boolean result = locationDao.setUserLocation(userId, postUserLocationReq);
            if (!result)
                throw new BaseException(USERS_EMPTY_USER_ID);
            return true;
        }
        catch (BaseException exception){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public Location changeUserDefaultLocation(long userId, PostUserLocationReq postUserLocationReq) throws BaseException{
        try{
            Location newDefaultLocation = locationDao.changeUserDefaultLocation(userId, postUserLocationReq);
            return newDefaultLocation;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void deleteUserLocation(long id) throws BaseException{
        try{
            locationDao.deleteUserLocation(id);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<Location> getUserLocations(long userId) throws BaseException {
        try{
            List<Location> locations = locationDao.getUserLocations(userId);
            return locations;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
