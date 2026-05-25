package FYP.CREDITRISK;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Value("${python.exe.path}")
    private String pythonExePath;

    @Value("${python.script.path}")
    private String scriptPath;

    @GetMapping("/companies")
    public String viewCompanies(Model model) {
        List<Company> listCompanies = companyRepository.findAll();
        model.addAttribute("listCompanies", listCompanies);
        return "companies";
    }

    @GetMapping("/companies/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "add_company";
    }

    private String[] callPythonScriptAndGetResult(double[] features) {
        try {
            List<String> command = new ArrayList<>();
            command.add(pythonExePath);
            command.add(scriptPath);
            for (double feature : features) {
                command.add(String.valueOf(feature));
            }

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String prediction = null;
            StringBuilder imageBase64Builder = new StringBuilder();
            String line;
            boolean firstLine = true;

            while ((line = in.readLine()) != null) {
                if (firstLine) {
                    prediction = line.trim();
                    firstLine = false;
                } else {
                    imageBase64Builder.append(line.trim());
                }
            }

            in.close();
            process.waitFor();

            return new String[]{prediction, imageBase64Builder.toString()};

        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"Error in prediction", null};
        }
    }

    @PostMapping("/companies/save")
    public String saveCompany(@Valid Company company, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add_company";
        }

        Company savedCompany = companyRepository.save(company);

        double[] features = new double[]{
            savedCompany.getRetainedEarnings(),
            savedCompany.getTotalDebt_TotalAsset(),
            savedCompany.getEmployees(),
            savedCompany.getTotalAsset_TotalLiabilities(),
            savedCompany.getGrossProfit_Rev(),
            savedCompany.getCash(),
            savedCompany.getEbit_totalAsset(),
            savedCompany.getEbit_Rev(),
            savedCompany.getNetCashFlow()
        };

        String[] result = callPythonScriptAndGetResult(features);
        String prediction = result[0];
        String imageBase64 = result[1];

        // Debug - print to console
        System.out.println("Prediction: " + prediction);
        System.out.println("Base64 length: " + (imageBase64 != null ? imageBase64.length() : "null"));
        
        model.addAttribute("prediction", prediction);
        model.addAttribute("company", savedCompany);
        model.addAttribute("limeImage", "data:image/png;base64," + imageBase64);

        

        return "company_prediction_result";
    }

    @GetMapping("/companies/edit/{id}")
    public String editCompany(@PathVariable("id") Integer id, Model model) {
        Company company = companyRepository.getReferenceById(id);
        model.addAttribute("company", company);
        return "edit_company";
    }

    @PostMapping("/companies/edit/{id}")
    public String saveUpdatedCompany(@PathVariable("id") Integer id, @Valid Company company,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_company";
        }
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable("id") Integer id) {
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }
}