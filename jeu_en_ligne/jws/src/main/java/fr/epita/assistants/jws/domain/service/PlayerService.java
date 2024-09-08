package fr.epita.assistants.jws.domain.service;
import fr.epita.assistants.jws.converter.Converter;
import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.utils.GameStateUtils;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.listAll;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//@ApplicationScoped
public class PlayerService {
        @Inject
    PlayerRepository playerRepository;
    PlayerEntity playerEntity;
    public PlayerService(Long id) {
        //GameModel game = GameRepository.findId(id);
        if (PlayerRepository.findId(id) != null) {
            this.playerEntity = new PlayerEntity(PlayerRepository.findId(id).id, PlayerRepository.findId(id).name, PlayerRepository.findId(id).lives, PlayerRepository.findId(id).posx, PlayerRepository.findId(id).posy);
        }
        else {
            playerEntity = null;
        }
    }
   // @Transactional
    public boolean exists(Long id) {
        return PlayerRepository.exists(id);
    }
}
