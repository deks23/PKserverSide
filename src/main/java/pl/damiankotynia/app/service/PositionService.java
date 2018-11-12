package pl.damiankotynia.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.damiankotynia.app.StringUtils;
import pl.damiankotynia.app.exceptions.StringPreparingException;
import pl.damiankotynia.app.model.Position;
import pl.damiankotynia.app.model.User;
import pl.damiankotynia.app.repository.PositionRepository;
import pl.damiankotynia.app.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PositionService {
    public boolean savePosition(String id, String longitude, String latitude) throws StringPreparingException{
        Optional <User> user= userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            Position position = new Position();
            position.setLatitude(StringUtils.parseToDouble(latitude));
            position.setLongitude(StringUtils.parseToDouble(longitude));
            position.setUser(user.get());
            position.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            positionRepository.save(position);
            return true;
        }

        return false;
    }

    public boolean getPosition(){

        return false;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    PositionRepository positionRepository;

    Logger logger = LoggerFactory.getLogger(PositionService.class);
}
