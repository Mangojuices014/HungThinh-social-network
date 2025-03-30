package com.hungthinh.socalnetwork_hungthinh.model;

import com.hungthinh.socalnetwork_hungthinh.util.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column( nullable = false)
    private String password;

    private String email;

    private String dcId;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(name = "last_active")
    private Instant lastActive;

    @OneToMany(mappedBy = "senderUser")
    private Set<Message> MessagesSent;

    @OneToMany(mappedBy = "recipientUser")
    private Set<Message> MessagesReceived;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

//    @OneToMany(mappedBy = "user")
//    private List<Post> posts;
//
//
//    @OneToMany(mappedBy = "user")
//    private Set<UserLikePost> userLikePost;

    public User(String fullName, String username, String password, Instant lastActive) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.lastActive = lastActive;
    }

}
