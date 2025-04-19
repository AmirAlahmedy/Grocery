FROM spectolabs/hoverfly:v1.11.0

# Expose Hoverfly's default ports
EXPOSE 8500
EXPOSE 8888

# Set the working directory in the container
WORKDIR /hoverfly

# Copy the simulation configuration file into the container
COPY ./config/* /hoverfly/config/*


# Define the default command to run Hoverfly in webserver mode
ENTRYPOINT ["hoverfly", "-listen-on-host", "0.0.0.0", "-import", "/hoverfly/config/simulation.json", "-webserver"]