package fr.epita.assistants.jws.data.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name = "player")
public class PlayerModel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "lastbomb") public Timestamp lastbomb;
    @Column(name = "lastmovement") public Timestamp lastmovement;
    @Column(name = "lives") public int lives;
    @Column(name = "name") public String name;
    @Column(name = "posx") public int posx;
    @Column(name = "posy") public int posy;
    @Column(name = "position") public int position;
/*    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false) public Long game_id;
    @ManyToMany(mappedBy = "players") List<GameModel> games;*/
    @ManyToOne
    @JoinColumn(name = "game_id")
    public GameModel game;
   /* @ManyToMany
    @JoinTable(
            name = "game_player",
            joinColumns = {@JoinColumn(name = "players_id")},
            inverseJoinColumns = {@JoinColumn(name = "gamemodel_id")})
    List<PlayerModel> playerModels;
*/
}