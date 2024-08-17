// Import the express module
const express = require('express');

// Create an instance of express
const app = express();

// Define a port to listen on
const port = 3000;

// Create a GET endpoint
app.get('/user', (req, res) => {
    // Define the JSON data you want to return
    const Person = [
        {
            firstName: "John Doe",
          lastName: "rrrrrr"
        },
        {
            firstName: "Jane Doe",
          lastName: "uuuuuuuuuuu"
        }
      ];
  
    // Send the JSON response
    res.json(Person);
  });

// Start the server and listen on the specified port
app.listen(port, () => {
  console.log(`Server 55 running on http://localhost:${port}`);
});
