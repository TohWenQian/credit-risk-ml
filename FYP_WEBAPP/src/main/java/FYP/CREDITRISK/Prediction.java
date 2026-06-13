package FYP.CREDITRISK;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prediction")
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "predicted_class", nullable = false, length = 50)
    private String predictedClass;

    @Column(name = "lime_image_base64", columnDefinition = "LONGTEXT")
    private String limeImageBase64;

    @Column(name = "assessed_by", length = 100)
    private String assessedBy;

    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public String getPredictedClass() { return predictedClass; }
    public void setPredictedClass(String predictedClass) { this.predictedClass = predictedClass; }
    public String getLimeImageBase64() { return limeImageBase64; }
    public void setLimeImageBase64(String limeImageBase64) { this.limeImageBase64 = limeImageBase64; }
    public String getAssessedBy() { return assessedBy; }
    public void setAssessedBy(String assessedBy) { this.assessedBy = assessedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
