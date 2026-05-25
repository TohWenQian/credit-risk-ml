import sys
import os
import numpy as np
import xgboost as xgb
import pandas as pd
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from lime.lime_tabular import LimeTabularExplainer
import base64
from io import BytesIO

# Load model
script_dir = os.path.dirname(os.path.abspath(__file__))
model_path = os.path.join(script_dir, "xgboost_model.json")
model = xgb.XGBClassifier()
model.load_model(model_path)

# Read input features
features = np.array([float(x) for x in sys.argv[1:]]).reshape(1, -1)

# Risk categories
risk_category_order = {
    'Lowest Risk': 0,
    'Low Risk': 1,
    'Medium Risk': 2,
    'High Risk': 3,
    'Very High Risk': 4,
    'Default': 5
}

# Make prediction
predicted_class_encoded = model.predict(features)
inverse_risk_category_order = {v: k for k, v in risk_category_order.items()}
predicted_class = inverse_risk_category_order[predicted_class_encoded[0]]

# LIME explanation
feature_names = [
    'Retained Earnings', 'Total debt/total asset', 'Employees',
    'total asset/total liabilities', 'gross profit/rev',
    'Cash', 'EBTI/total asset', 'EBTI/REV', 'Net Cash Flow'
]
class_names = ['Lowest Risk', 'Low Risk', 'Medium Risk', 'High Risk', 'Very High Risk', 'Default']

X_train_important = pd.read_csv(os.path.join(script_dir, "X_train_important.csv"))

lime_explainer = LimeTabularExplainer(
    X_train_important.values,
    feature_names=feature_names,
    class_names=class_names,
    mode='classification'
)

exp = lime_explainer.explain_instance(
    features[0],
    model.predict_proba,
    num_features=10,
    labels=[predicted_class_encoded[0]]
)

label_to_use = predicted_class_encoded[0] if predicted_class_encoded[0] in exp.local_exp else list(exp.local_exp.keys())[0]

fig = exp.as_pyplot_figure(label=label_to_use)

# Convert to base64 instead of saving file
buffer = BytesIO()
fig.savefig(buffer, format='png', bbox_inches='tight')
buffer.seek(0)
image_base64 = base64.b64encode(buffer.read()).decode('utf-8')
plt.close(fig)

# Print outputs — one line each
print(predicted_class)
print(image_base64)