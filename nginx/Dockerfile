FROM nginx:latest
COPY nginx.conf /etc/nginx/nginx.conf
# COPY dist/frontend /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
