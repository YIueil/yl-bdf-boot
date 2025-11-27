package cc.yiueil.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ACT_DE_MODEL", uniqueConstraints = {
        @UniqueConstraint(name = "uk_act_de_model_name", columnNames = "name")
})
public class Model {

    public static final int MODEL_TYPE_BPMN = 0;
    public static final int MODEL_TYPE_FORM = 2;
    public static final int MODEL_TYPE_APP = 3;
    public static final int MODEL_TYPE_DECISION_TABLE = 4;

    @Id
    @GeneratedValue(generator = "modelIdGenerator")
    @GenericGenerator(name = "modelIdGenerator", strategy = "uuid2")
    @Column(name = "id", unique = true, length = 50)
    protected String id;

    @Column(name = "name", nullable = false, length = 100)
    protected String name;

    @Column(name = "model_key")
    protected String key;

    @Column(name = "description")
    protected String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    protected Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    protected Date lastUpdated;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "version")
    protected int version;

    @Column(name = "model_editor_json", columnDefinition = "text")
    protected String modelEditorJson;

    @Column(name = "model_comment")
    protected String comment;

    @Column(name = "model_type")
    protected Integer modelType;

    @Column(name = "thumbnail")
    private byte[] thumbnail;

    @Column(name = "deployment_id")
    private String deploymentId;

    public Model() {
        this.created = new Date();
    }

}