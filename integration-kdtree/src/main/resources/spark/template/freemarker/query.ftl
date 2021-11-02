<#assign content>
  <h1> Stars </h1>

<#--  https://www.w3schools.com/tags/att_input_type_file.asp-->

  <div id="flexContainerDiv">
    <div id="flexDiv1">
      <div id="chooseFileDiv">
        <h3>
          Loaded File:
          <br>
          ${loaded_file}
        </h3>

        <form id="loadCsvForm" method="POST" action="/csvLoaded">
          <label for="fileName">Select a file:</label>
          <br>
          <input type="file" id="fileName" name="fileName">
          <br>
          <br>
          <input type="submit">
        </form>
      </div>

      <br>

      <div id="musicReminisceDiv">
        <iframe title="Reminisce / Bladerunner 2049" width="100%"
                src=${music_link}
                frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        ></iframe>
      </div>
    </div>

    <div id="starsInputDiv">
      <form method="POST" action="/results">
        <label for="threshold">Enter threshold (positive integer for neighbors | positive real for radius):</label>
        <br>
        <input name="threshold" type="number" step="any" min="0" placeholder="threshold" required>
        <br>
        <br>

        <label for="argsSize">How would you like to enter the constraints?</label>
        <br>
        <select name="argsSize" id="argsSize" required>
          <option disabled selected value> *Select an option* </option>
          <option value="3Args">I have the star name</option>
          <option value="5Args">I have the target coordinates</option>
        </select>
        <br>
        <br>

        <div id="argsDiv" hidden>
          <div id="3ArgsDiv">
            <label for="starName">Enter star name (no quotations):</label>
            <br>
            <input name="starName" id="starName" type="text" placeholder="star name">
          </div>

          <div id="5ArgsDiv">
            <label for="targetX">Enter target x-coordinate:</label>
            <br>
            <input name="targetX" id="targetX" type="number" step="any" placeholder="x-coordinate">
            <br>
            <br>

            <label for="targetY">Enter target y-coordinate:</label>
            <br>
            <input name="targetY" id="targetY" type="number" step="any" placeholder="y-coordinate">
            <br>
            <br>

            <label for="targetZ">Enter target z-coordinate:</label>
            <br>
            <input name="targetZ" id="targetZ" type="number" step="any" placeholder="z-coordinate">
          </div>
        </div>

        <br>

        <input type="radio" id="naive_neighbors" name="commandChoice" value="naive_neighbors" required>
        <label for="naive_neighbors">Naive Neighbors</label><br>
        <input type="radio" id="naive_radius" name="commandChoice" value="naive_radius">
        <label for="naive_radius">Naive Radius</label><br>
        <input type="radio" id="neighbors" name="commandChoice" value="neighbors">
        <label for="neighbors">Neighbors</label><br>
        <input type="radio" id="radius" name="commandChoice" value="radius">
        <label for="radius">Radius</label>
        <br>
        <br>

        <input type="submit">
      </form>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script>
    console.log("inner thingy")
    $(document).ready(function () {

      $('#argsSize').on('change', function () {
        $("#argsDiv").show();
        const selectedVal = $(this).val();
        console.log(selectedVal)

        console.log(selectedVal === "3Args")

        if (selectedVal === "3Args") {
          console.log("selected star name")
          $("#3ArgsDiv").show();
          $("#5ArgsDiv").hide();

          $("#starName").prop('required',true);
          $("#targetX").removeAttr('required');
          $("#targetY").removeAttr('required');
          $("#targetZ").removeAttr('required');
        } else if (selectedVal === "5Args") {
          console.log("selectd target coord")
          $("#5ArgsDiv").show();
          $("#3ArgsDiv").hide();

          $("#starName").removeAttr('required');
          $("#targetX").prop('required',true);
          $("#targetY").prop('required',true);
          $("#targetZ").prop('required',true);
        }
      });
    });
  </script>

</#assign>
<#include "main.ftl">