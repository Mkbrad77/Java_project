package fr.epita.assistants.jws.data.model;


import fr.epita.assistants.jws.utils.GameStateUtils;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "game")
public
class GameModel extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "state") public String state;
    @Column(name = "starttime") public Timestamp starttime;
     @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "game_player",
            joinColumns = { @JoinColumn(name = "gamemodel_id") },
            inverseJoinColumns = { @JoinColumn(name = "players_id") }
    )
    //@OneToMany
    //@JoinColumn(name = "game_id")
    public List<PlayerModel> players = new ArrayList<>();
    public @ElementCollection
    @CollectionTable(name = "game_map", joinColumns = @JoinColumn(name = "gamemodel_id")) List<String> map;
}
