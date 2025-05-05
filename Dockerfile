# Copyright 2025 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM clojure:tools-deps-bullseye-slim AS builder
WORKDIR /opt/app
COPY . .
RUN clj -T:build uber

FROM eclipse-temurin:24-jre-alpine

WORKDIR /opt/app
COPY --from=builder /opt/app/target/standalone.jar /opt/app/standalone.jar
EXPOSE 80
ENV PORT=80
ENTRYPOINT ["java", "-jar", "standalone.jar"]
