let currentView = null;

document.addEventListener("DOMContentLoaded", () => {
  const formClient = document.getElementById("formClient");
  const formService = document.getElementById("formService");
  const tableBody = document.querySelector("#clientTable tbody");
  const searchInput = document.getElementById("search");
  let clients = [];

  if (document.URL.includes("clientForm")) {
    currentView = "clients";
  } else if (document.URL.includes("servicesForm")) {
    currentView = "services";
  } else if (document.URL.includes("listClients")) {
    currentView = "listClients";
  } else if (document.URL.includes("index")) {
    currentView = "index";
  }

  if (currentView === "clients") {
    if (formClient) {
      formClient.addEventListener("submit", async function (event) {
        event.preventDefault();

        const client = {
          nombres: formClient.elements["nombres"].value,
          apellidos: formClient.elements["apellidos"].value,
          tipoIdentificacion: formClient.elements["tipoIdentificacion"].value,
          identificacion: formClient.elements["numeroIdentificacion"].value,
          fechaNacimiento: formClient.elements["fechaNacimiento"].value,
          numeroCelular: formClient.elements["numeroCelular"].value,
          correoElectronico: formClient.elements["correoElectronico"].value,
        };
        console.log(client);

        if (validateInfo(client)) {
          alert("Los datos no cumplen con el formato establecido.");
        } else {
          try {
            const response = await fetch(
              "http://localhost:8080/clientes/crear",
              {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify(client),
              }
            );
            if (response.ok) {
              if (response.status === 200) {
                alert("Cliente registrado");
              }
            } else {
              const errorMessage = await response.text();
              if (response.status === 409) {
                alert(errorMessage);
              } else {
                alert("Error al registrar el cliente");
              }
            }           
            resetFormClient();
          } catch (error) {
            if (
              error.name === "TypeError" &&
              error.message.includes("Failed to fetch")
            ) {
              alert("Error de conexión");
            } else {
              alert("Error: " + error.message);
            }
          }
        }
      });
    }
  }
  if (currentView === "services") {
    if (formService) {
      formService.addEventListener("submit", async function (event) {
        event.preventDefault();

        const servicio = {
          identificacion: formService.elements["numeroIdentificacion"].value,
          servicio: formService.elements["servicios"].value,
          fechaInicio: formService.elements["fechainicio"].value,
          ultimaFacturacion: formService.elements["facturacion"].value,
          ultimoPago: formService.elements["pago"].value,
        };

        if (validateInfo(servicio)) {
          alert("Los datos no cumplen con el formato establecido.");
        } else {
          try {
            const response = await fetch(
              "http://localhost:8080/servicios/crear",
              {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify(servicio),
              }
            );

            if (response.ok) {
              if (response.status === 200) {
                alert("Servicio registrado");
              }
            } else {
              const errorMessage = await response.text();
              if (response.status === 409) {
                alert(errorMessage);
              } else {
                alert("Error al registrar el servicio");
              }
            }
            resetFormService();
          } catch (error) {
            if (
              error.name === "TypeError" &&
              error.message.includes("Failed to fetch")
            ) {
              alert("Error de conexión");
            } else {
              alert("Error: " + error.message);
            }
          }
        }
      });
    }
  }

  function resetFormClient() {
    formClient.elements["nombres"].value = "";
    formClient.elements["apellidos"].value = "";
    formClient.elements["tipoIdentificacion"].value = "";
    formClient.elements["numeroIdentificacion"].value = "";
    formClient.elements["fechaNacimiento"].value = "";
    formClient.elements["numeroCelular"].value = "";
    formClient.elements["correoElectronico"].value = "";
  }

  function resetFormService() {
    formService.elements["numeroIdentificacion"].value = "";
    formService.elements["servicios"].value = "";
    formService.elements["fechainicio"].value = "";
    formService.elements["facturacion"].value = "";
    formService.elements["pago"].value = "";
  }

  function validateInfo(data) {
    let identification;
    let telephone;
    let flag = false;

    if (data?.identificacion) {
      identification = data.identificacion;
      if (identification.length < 6 && identification.length > 10) {
        flag = true;
        return flag;
      }
    } else if (data?.numeroCelular) {
      telephone = data.numeroCelular;
      if (telephone.length !== 10) {
        flag = true;
        return flag;
      }
    }

    return flag;
  }
  if (currentView === "listClients") {
    async function fetchClients() {
      try {
        const response = await fetch("http://localhost:8080/clientes/all"); //
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        clients = await response.json();
        populateTable(clients);
      } catch (error) {
        console.error("Error fetching clients:", error);
      }
    }

    function populateTable(data) {
      tableBody.innerHTML = "";
      data.forEach((client) => {
        const serviciosHtml = client.servicios
          .map((servicio) => {
            return `
                      <div>
                        <span>${servicio.servicio}</span>                        
                      </div>
                    `;
          })
          .join("");

        const row = document.createElement("tr");
        row.innerHTML = `
                    <td>${client.nombres}</td>
                    <td>${client.apellidos}</td>
                    <td>${client.tipoIdentificacion}</td>
                    <td>${client.identificacion}</td>
                    <td>${client.fechaNacimiento}</td>
                    <td>${client.numeroCelular}</td>
                    <td>${client.correoElectronico}</td> 
                    <td>${serviciosHtml}</td>
                    <td><button class="btn-delete">Eliminar</button></td>              
                `;
        tableBody.appendChild(row);
      });
    }

    function searchClients() {
      const searchTerm = searchInput.value.toLowerCase();
      const filteredClients = clients.filter((client) =>
        client.identificacion.toLowerCase().includes(searchTerm)
      );
      populateTable(filteredClients);
    }

    fetchClients();
    searchInput.addEventListener("input", searchClients);


    tableBody.addEventListener("click", async (e) => {
      if (e.target.classList.contains("btn-delete")) {
        e.preventDefault();
        const row = e.target.closest("tr");
        const clientId = row.cells[3].textContent.trim();
  
        try {
          const response = await fetch(`http://localhost:8080/clientes/${clientId}`, {
            method: "DELETE",
          });
  
          if (!response.ok) {
            alert("Ocurrió un error al eliminar el cliente");
          } else {
            alert("Cliente eliminado");
          }
                      
          row.remove();
        } catch (error) {
          alert("Ocurrió un error: " + error);
        }
      }
    });
  }  
});
